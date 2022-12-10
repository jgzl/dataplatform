package cn.cleanarch.dp.gateway.decorator;

import cn.cleanarch.dp.gateway.domain.GatewayLogDO;
import cn.cleanarch.dp.gateway.util.LogUtils;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
@Slf4j
public class PartnerServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    private final GatewayLogDO gatewayLog;
    private final ServerHttpResponse response;
    private final ServerWebExchange delegate;

    public PartnerServerHttpResponseDecorator(ServerWebExchange delegate,ServerHttpResponse response, GatewayLogDO gatewayLog) {
        super(response);
        this.gatewayLog = gatewayLog;
        this.response = response;
        this.delegate = delegate;
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return super.writeAndFlushWith(body);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        LocalDateTime updateTime = LocalDateTime.now();
        long costTime = DateUtil.between(DateUtil.date(gatewayLog.getRequestTime()), DateUtil.date(updateTime), DateUnit.MS);
        gatewayLog.setTargetService(getRoute(delegate).getId());
        gatewayLog.setExecuteTime(costTime);
        gatewayLog.setResponseTime(updateTime);
        gatewayLog.setHttpStatus(response.getStatusCode() != null ? response.getStatusCode().value() + "" : null);
        final MediaType contentType = super.getHeaders().getContentType();
        if (LogUtils.legalLogMediaTypes.contains(contentType)) {
            if (body instanceof Mono) {
                final Mono<DataBuffer> monoBody = (Mono<DataBuffer>) body;
                return super.writeWith(monoBody.map(dataBuffer -> LogUtils.logging(gatewayLog, dataBuffer)));
            } else if (body instanceof Flux) {
                final Flux<DataBuffer> monoBody = (Flux<DataBuffer>) body;
                return super.writeWith(monoBody.buffer().map(dataBuffers -> LogUtils.logging(gatewayLog, dataBuffers)));
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("网关只记录非xml,json格式的请求内容,当前请求的Content-Type为{}", contentType);
            }
            LogUtils.logging(gatewayLog);
        }
        log.info("结束访问[{}],合计共消耗时间为:{}ms", gatewayLog.getRequestPath(), gatewayLog.getExecuteTime());
        return super.writeWith(body);
    }

    private Route getRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }
}