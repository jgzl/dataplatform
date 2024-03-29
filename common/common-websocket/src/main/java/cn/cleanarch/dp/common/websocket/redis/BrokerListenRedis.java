package cn.cleanarch.dp.common.websocket.redis;

import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 客户端心跳缓存
 *
 * @author li7hai26@outlook.com 2019/05/30 15:17
 */
public class BrokerListenRedis extends WebSocketRedis {

    /**
     * 生成redis存储key
     *
     * @return key
     */
    private static String getCacheKey() {
        return WebSocketConstant.REDIS_KEY + "socket-nodes:all";
    }

    /**
     * 刷新缓存
     *
     * @param brokerId 客户端唯一标识
     */
    public static void refreshCache(String brokerId) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.hshPut(getCacheKey(), brokerId, StrUtil.EMPTY);
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 查询缓存
     */
    public static List<String> getCache() {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        Set<String> set;
        try {
            set = ObjectUtil.defaultIfNull(redisHelper.hshKeys(getCacheKey()), new HashSet<>());
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return new ArrayList<>(set);
    }

    /**
     * 清空缓存
     *
     * @param brokerId 客户端唯一标识
     */
    public static void clearRedisCache(String brokerId) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.hshDelete(getCacheKey(), brokerId);
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }
}
