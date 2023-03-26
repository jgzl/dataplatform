package cn.cleanarch.dp.gateway.fish.filter.factory;

import cn.cleanarch.dp.gateway.fish.filter.ClientIdGatewayFilter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * @Description 自定义clientId安全验证过滤器工厂
 * @Author jianglong
 * @Date 2020/05/27
 * @Version V1.0
 */
@Slf4j
@Component
public class SecureClientIdGatewayFilterFactory extends AbstractGatewayFilterFactory<SecureClientIdGatewayFilterFactory.Config> {

    /**
     在yml中配置
     filters:
     # 关键在下面一句，值为true则开启认证，false则不开启
     # 这种配置方式和spring cloud gateway内置的GatewayFilterFactory一致
     # 注意SecureClientId对应SecureClientIdGatewayFilterFactory，GatewayFilterFactory为默认固定后缀,gateway只匹配前缀
     - SecureClientId=true
     */

    public SecureClientIdGatewayFilterFactory() {
        super(Config.class);
        log.info("Loaded GatewayFilterFactory [SecureClientId]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("enabled");
    }

    /**
     * 核心执行方法
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isEnabled()){
                Route route = exchange.getRequiredAttribute(GATEWAY_ROUTE_ATTR);
                return new ClientIdGatewayFilter(route.getId()).filter(exchange, chain);
            }
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config{
        // 控制是否开启认证
        private boolean enabled;
    }
}
