package cn.cleanarch.gw.common.gateway.configuration;

import cn.cleanarch.gw.common.core.constant.CacheConstants;
import cn.cleanarch.gw.common.gateway.support.GatewayAccessConfCacheHolder;
import cn.cleanarch.gw.gateway.admin.gateway.vo.GatewayAccessConfVO;
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
public class DynamicGatewayAccessConfAutoConfiguration {

    @Bean
    public RedisMessageListenerContainer gatewayAccessConfRedisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((message, bytes) -> {
            log.warn("接收到重新加载网关访问事件");
            GatewayAccessConfCacheHolder.removeList();
            RedisTemplate<String,Object> redisTemplate = SpringUtil.getBean(new TypeReference<RedisTemplate<String,Object>>() {});
            List<GatewayAccessConfVO> values = redisTemplate.<String, GatewayAccessConfVO>opsForHash().values(CacheConstants.ACCESS_CONF_KEY);
            if (CollUtil.isEmpty(values)) {
                values = ListUtil.empty();
            }
            GatewayAccessConfCacheHolder.setList(values);
        }, new ChannelTopic(CacheConstants.ACCESS_CONF_JVM_RELOAD_TOPIC));
        return container;
    }

}
