package cn.cleanarch.gw.common.redis.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate 配置
 *
 * @author lihaifeng
 * @date 2021/12/24
 */
@Configuration
public class ReactiveRedisTemplateConfiguration {

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext
                .<String, Object>newSerializationContext()
                .key(RedisSerializer.string()).value(RedisObjectMapper.getJackson2JsonRedisSerializer())
                .hashKey(RedisSerializer.string()).hashValue(RedisObjectMapper.getJackson2JsonRedisSerializer())
                .build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, serializationContext);
    }
}