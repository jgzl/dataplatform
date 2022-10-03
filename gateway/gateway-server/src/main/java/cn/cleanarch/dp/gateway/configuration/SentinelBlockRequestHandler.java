package cn.cleanarch.dp.gateway.configuration;

import cn.cleanarch.dp.common.core.model.R;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author vlfac
 */
public class SentinelBlockRequestHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable e) {
        R<String> result = R.error();
        if (e instanceof FlowException) {
            result = R.error("触发Sentinel限流了!");
        } else if (e instanceof DegradeException) {
            result = R.error("触发Sentinel服务降级了!");
        } else if (e instanceof ParamFlowException) {
            result = R.error("触发Sentinel参数限流了!");
        } else if (e instanceof SystemBlockException) {
            result = R.error("触发Sentinel系统保护了!");
        } else if (e instanceof AuthorityException) {
            result = R.error("触发Sentinel授权异常了!");
        } else {
            result = R.error("触发Sentinel未知[{"+e.getMessage()+"}]异常了!");
        }
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .bodyValue(result);
    }

    /**
     * Reference from {@code DefaultErrorWebExceptionHandler} of Spring Boot.
     */
    private boolean acceptsHtml(ServerWebExchange exchange) {
        try {
            List<MediaType> acceptedMediaTypes = exchange.getRequest().getHeaders().getAccept();
            acceptedMediaTypes.remove(MediaType.ALL);
            MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);
            return acceptedMediaTypes.stream()
                    .anyMatch(MediaType.TEXT_HTML::isCompatibleWith);
        } catch (InvalidMediaTypeException ex) {
            return false;
        }
    }

}
