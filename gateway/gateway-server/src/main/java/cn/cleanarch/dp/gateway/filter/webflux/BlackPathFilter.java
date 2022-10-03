package cn.cleanarch.dp.gateway.filter.webflux;

import cn.cleanarch.dp.gateway.configuration.properties.GatewayPathProperties;
import cn.cleanarch.dp.gateway.configuration.properties.GatewayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 黑名单过滤器
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/21
 */
@Slf4j
@RequiredArgsConstructor
public class BlackPathFilter implements WebFilter {

    private static final AntPathMatcher matcher = new AntPathMatcher();

    private final GatewayProperties properties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String path = uri.getPath();
        GatewayPathProperties propertiesPath = properties.getPath();
        List<String> blackPaths = propertiesPath.getBlackPath();
        blackPaths.forEach(p -> log.info("白名单地址为:{}", p));
        return chain.filter(exchange);
    }
}