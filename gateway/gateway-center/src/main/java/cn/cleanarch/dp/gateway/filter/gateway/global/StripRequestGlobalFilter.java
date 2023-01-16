//package cn.cleanarch.dp.gateway.filter.gateway.global;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;
//
///**
// * 网关全局过滤器器-作用所有的微服务
// * 1. 对请求头中参数进行处理参数进行清洗
// * 2. 重写StripPrefix = 1,支持全局
// *
// * @author li7hai26@gmail.com
// * @date 2021/10/8
// */
//@Slf4j
//@Component
//public class StripRequestGlobalFilter implements GlobalFilter, Ordered {
//    /**
//     * Process the Web request and (optionally) delegate to the next {@code WebFilter}
//     * through the given {@link GatewayFilterChain}.
//     *
//     * @param exchange the current server exchange
//     * @param chain    provides a way to delegate to the next filter
//     * @return {@code Mono<Void>} to indicate when request processing is complete
//     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 1. 清洗请求头中参数
//        ServerHttpRequest request = exchange.getRequest();
////        request.mutate().headers(httpHeaders -> httpHeaders.remove(HttpHeadConstants.X_DATAPLATFORM_ENV)).build();
//        // 2. 重写StripPrefix
//        addOriginalRequestUrl(exchange, request.getURI());
//        String rawPath = request.getURI().getRawPath();
//        String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/")).skip(1L)
//                .collect(Collectors.joining("/"));
//        ServerHttpRequest newRequest = request.mutate().path(newPath).build();
//        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
//        return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
//    }
//
//    @Override
//    public int getOrder() {
//        return -1000;
//    }
//}
