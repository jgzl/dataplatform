package cn.cleanarch.dp.common.gateway.support;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author li7hai26@gmail.com
 * @date 2021/11/19
 * <p>
 * 动态路由检查检查
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicRouteHealthIndicator extends AbstractHealthIndicator {

    private final RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(CacheConstants.ROUTE_KEY))) {
            builder.up();
        } else {
            log.warn("动态路由监控检查失败，当前无路由配置");
            builder.down();
        }
    }

}
