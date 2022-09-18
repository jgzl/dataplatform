package cn.cleanarch.gw.common.gateway.configuration;

import cn.cleanarch.gw.common.core.constant.CacheConstants;
import cn.cleanarch.gw.common.gateway.exception.RouteCheckException;
import cn.cleanarch.gw.common.gateway.support.DynamicRouteHealthIndicator;
import cn.cleanarch.gw.common.gateway.support.RouteDefinitionCacheHolder;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author li7hai26@gmail.com
 * @date 2021/11/5
 * <p>
 * 动态路由配置类
 */
@Slf4j
@Configuration
public class DynamicGatewayRouteConfAutoConfiguration {

    /**
     * 配置文件设置为空 redis 加载为准
     *
     * @return
     */
    @Bean
    public PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator() {
        return new PropertiesRouteDefinitionLocator(new GatewayProperties());
    }


    /**
     * 动态路由监控检查
     *
     * @param redisTemplate redis
     * @return
     */
    @Bean
    public DynamicRouteHealthIndicator healthIndicator(RedisTemplate<String,Object> redisTemplate) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(CacheConstants.ROUTE_KEY))) {
            throw new RouteCheckException("路由信息未初始化，网关启动失败");
        }
        return new DynamicRouteHealthIndicator(redisTemplate);
    }

    @Bean
    public RedisMessageListenerContainer gatewayRouteConfRedisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((message, bytes) -> {
            log.warn("接收到重新加载网关路由事件");
            RouteDefinitionCacheHolder.removeRouteList();
            // 发送刷新路由事件
            SpringUtil.publishEvent(new RefreshRoutesEvent(this));
        }, new ChannelTopic(CacheConstants.ROUTE_JVM_RELOAD_TOPIC));
        return container;
    }
}
