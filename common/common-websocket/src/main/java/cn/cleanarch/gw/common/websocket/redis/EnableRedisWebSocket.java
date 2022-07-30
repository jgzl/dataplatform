package cn.cleanarch.gw.message.configuration.websocket.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author lihaifeng
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({RedisWebSocketConfiguration.class})
public @interface EnableRedisWebSocket {
}
