package cn.cleanarch.gw.common.gateway.configuration;

import cn.cleanarch.gw.common.core.exception.enums.GlobalErrorCodeConstants;
import cn.cleanarch.gw.common.core.handler.reactive.GlobalExceptionHandler;
import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.core.utils.WebfluxUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author li7hai26@gmail.com
 * @date 2021/5/23
 * <p>
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 {@link org.springframework.web.server.handler.ResponseStatusExceptionHandler} 执行
 */
@Slf4j
@RequiredArgsConstructor
@Order(-1)
@Configuration
public class GatewayExceptionHandlerConfiguration implements ErrorWebExceptionHandler {
    
    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        String msg;
        R<Void> result;
        if (ex instanceof NotFoundException) {
            URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
            if (url!=null) {
                msg = String.format("无法找到%s服务",url.getHost());
                result = R.error(GlobalErrorCodeConstants.SERVICE_UNAVAILABLE.getCode(),msg);
            } else {
                result = R.error(GlobalErrorCodeConstants.SERVICE_UNAVAILABLE);
            }
        } else {
            result = globalExceptionHandler.allExceptionHandler(exchange, ex);
        }
        log.error("[网关异常处理]请求路径:[" + exchange.getRequest().getPath() + "],异常信息:", ex);
        return WebfluxUtil.errorOut(response, result);
    }

}