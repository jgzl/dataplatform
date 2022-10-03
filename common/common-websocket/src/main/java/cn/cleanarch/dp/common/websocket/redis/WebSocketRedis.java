package cn.cleanarch.dp.common.websocket.redis;

import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.common.websocket.config.WebSocketConfig;
import cn.hutool.extra.spring.SpringUtil;

/**
 * websocket缓存父类
 *
 * @author shuangfei.zhu@hand-china.com 2020/09/01 20:24
 */
public abstract class WebSocketRedis {

    private volatile static RedisHelper redisHelper;
    private volatile static WebSocketConfig config;

    protected WebSocketRedis() {
    }

    protected static RedisHelper getRedisHelper() {
        if (null == redisHelper) {
            synchronized (SessionRedis.class) {
                if (null == redisHelper) {
                    redisHelper = SpringUtil.getBean(RedisHelper.class);
                }
            }
        }
        return redisHelper;
    }

    protected static WebSocketConfig getConfig() {
        if (null == config) {
            synchronized (SessionRedis.class) {
                if (null == config) {
                    config = SpringUtil.getBean(WebSocketConfig.class);
                }
            }
        }
        return config;
    }
}
