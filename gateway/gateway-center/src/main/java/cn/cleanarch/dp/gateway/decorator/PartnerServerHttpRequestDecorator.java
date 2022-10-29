package cn.cleanarch.dp.gateway.decorator;

import cn.cleanarch.dp.gateway.domain.GatewayLogDO;
import cn.cleanarch.dp.gateway.common.WebEnum;
import cn.cleanarch.dp.gateway.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import static reactor.core.scheduler.Schedulers.single;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
@Slf4j
public class PartnerServerHttpRequestDecorator extends ServerHttpRequestDecorator {
    private final Flux<DataBuffer> body;

    PartnerServerHttpRequestDecorator(ServerHttpRequest request, GatewayLogDO gatewayLog) {
        super(request);
        final MediaType contentType = request.getHeaders().getContentType();

        Flux<DataBuffer> flux = super.getBody();
        if (contentType == null || LogUtils.legalLogMediaTypes.contains(contentType)) {
            body = flux.publishOn(single()).map(dataBuffer -> LogUtils.logging(gatewayLog, dataBuffer, WebEnum.REQUEST));
        } else {
            if (log.isDebugEnabled()) {
                log.debug("网关只记录xml,json格式的请求相应内容,当前请求的Content-Type为{}", contentType);
            }
            body = flux;
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }

}
