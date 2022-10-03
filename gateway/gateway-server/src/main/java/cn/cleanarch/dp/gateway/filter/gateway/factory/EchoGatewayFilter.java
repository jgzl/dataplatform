package cn.cleanarch.dp.gateway.filter.gateway.factory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * 网关可选过滤器-回声测试
 *
 * @author li7hai26@gmail.com
 * @date 2021/5/19
 */
@Slf4j
@Component
@AllArgsConstructor
public class EchoGatewayFilter extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("回声测试请求路径:{}",request.getPath());
            return chain.filter(exchange);
        };
    }

}
