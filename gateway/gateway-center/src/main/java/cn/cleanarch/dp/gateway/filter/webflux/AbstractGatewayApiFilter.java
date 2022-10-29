package cn.cleanarch.dp.gateway.filter.webflux;

import cn.cleanarch.dp.gateway.configuration.properties.GatewayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 抽象网关过滤器
 * @author li7hai26@gmail.com
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractGatewayApiFilter implements WebFilter {

    private final GatewayProperties gatewayProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String rawPath = uri.getRawPath();
        String apiPrefix = gatewayProperties.getApiPrefix();
        /**
         * 记录网关统一前缀访问路径访问的日志信息
         */
        if (rawPath.startsWith(apiPrefix)) {
            return match(exchange,chain);
        } else {
            return disMatch(exchange,chain);
        }
    }

    public abstract Mono<Void> match(ServerWebExchange exchange, WebFilterChain chain);

    public Mono<Void> disMatch(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange);
    }
}
