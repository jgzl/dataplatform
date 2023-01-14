package cn.cleanarch.dp.common.gateway.configuration;

import cn.cleanarch.dp.common.core.exception.enums.GlobalErrorCodeConstants;
import cn.cleanarch.dp.common.core.handler.reactive.GlobalExceptionHandler;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.JacksonUtil;
import cn.cleanarch.dp.common.core.utils.WebfluxUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    private static final Set<String> DISCONNECTED_CLIENT_EXCEPTIONS;

    /**
     * 排除部份异常不做自定义信息包装;
     */
    static {
        Set<String> exceptions = new HashSet<>();
        exceptions.add("AbortedException");
        exceptions.add("ClientAbortException");
        exceptions.add("EOFException");
        exceptions.add("EofException");
        DISCONNECTED_CLIENT_EXCEPTIONS = Collections.unmodifiableSet(exceptions);
    }

    /**
     * 执行入口
     * @param exchange
     * @param ex
     * @return
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted() || isDisconnectedClientError(ex)) {
            return Mono.error(ex);
        }else {
            log.error("服务异常!", ex);
        }
        ServerHttpRequest request = exchange.getRequest();
        String rawQuery = request.getURI().getRawQuery();
        String query = StringUtils.hasText(rawQuery) ? "?" + rawQuery : "";
        String path = request.getPath() + query ;
        String message ;
        HttpStatus status = determineStatus(ex);
        if (status == null){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        // 通过状态码自定义异常信息
        if (status.value() >= HttpStatus.BAD_REQUEST.value()
                && status.value() < HttpStatus.INTERNAL_SERVER_ERROR.value()){
            message = "路由服务不可达或禁止访问！";
        }else {
            message = "路由服务异常！";
        }
        message += " path：" + path;
        return WebfluxUtil.out(exchange.getResponse(),status,R.error(message));
    }

    /**
     * 获取异常中的状态对象
     * @param ex
     * @return
     */
    @Nullable
    protected HttpStatus determineStatus(Throwable ex) {
        if (ex instanceof ResponseStatusException) {
            return ((ResponseStatusException) ex).getStatus();
        }
        return null;
    }

    /**
     * 是否为排除异常
     * @param ex
     * @return
     */
    private boolean isDisconnectedClientError(Throwable ex) {
        return DISCONNECTED_CLIENT_EXCEPTIONS.contains(ex.getClass().getSimpleName())
                || isDisconnectedClientErrorMessage(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
    }

    /**
     * 是否为已断开连接的客户端异常
     * @param message
     * @return
     */
    private boolean isDisconnectedClientErrorMessage(String message) {
        message = (message != null) ? message.toLowerCase() : "";
        return (message.contains("broken pipe") || message.contains("connection reset by peer"));
    }

//    private final GlobalExceptionHandler globalExceptionHandler;
//
//    @Override
//    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
//        ServerHttpResponse response = exchange.getResponse();
//
//        if (response.isCommitted()) {
//            return Mono.error(ex);
//        }
//
//        String msg;
//        R<Void> result;
//        if (ex instanceof NotFoundException) {
//            URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
//            if (url!=null) {
//                msg = String.format("无法找到%s服务",url.getHost());
//                result = R.error(GlobalErrorCodeConstants.SERVICE_UNAVAILABLE.getCode(),msg);
//            } else {
//                result = R.error(GlobalErrorCodeConstants.SERVICE_UNAVAILABLE);
//            }
//        } else {
//            result = globalExceptionHandler.allExceptionHandler(exchange, ex);
//        }
//        log.error("[网关异常处理]请求路径:[" + exchange.getRequest().getPath() + "],异常信息:", ex);
//        return WebfluxUtil.errorOut(response, result);
//    }



}