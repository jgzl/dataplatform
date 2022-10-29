package cn.cleanarch.dp.common.gateway.configuration;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.common.gateway.support.GatewayAccessCacheHolder;
import cn.cleanarch.dp.gateway.vo.GatewayAccessVO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.List;

/**
 * @author li7hai26@gmail.com
 * @date 2021/11/5
 * <p>
 * 动态路由配置类
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class DynamicGatewayAccessAutoConfiguration {

    @Bean
    public RedisMessageListenerContainer gatewayAccessConfRedisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((message, bytes) -> {
            log.warn("接收到重新加载网关访问事件");
            GatewayAccessCacheHolder.removeList();
            RedisTemplate<String,Object> redisTemplate = SpringUtil.getBean(new TypeReference<RedisTemplate<String,Object>>() {});
            List<GatewayAccessVO> values = redisTemplate.<String, GatewayAccessVO>opsForHash().values(CacheConstants.ACCESS_KEY);
            if (CollUtil.isEmpty(values)) {
                values = ListUtil.empty();
            }
            GatewayAccessCacheHolder.setList(values);
        }, new ChannelTopic(CacheConstants.ACCESS_JVM_RELOAD_TOPIC));
        return container;
    }

}
