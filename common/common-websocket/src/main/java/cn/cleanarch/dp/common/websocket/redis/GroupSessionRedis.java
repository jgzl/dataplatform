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
 * 存储分组与分组所属的sessionId
 *
 * @author li7hai26@outlook.com 2020/09/02 9:46
 */
public class GroupSessionRedis extends WebSocketRedis {

    /**
     * 生成redis存储key
     *
     * @param group 分组
     * @return key
     */
    private static String getCacheKey(String group) {
        return WebSocketConstant.REDIS_KEY + "group-sessions:" + group;
    }

    /**
     * 添加缓存
     *
     * @param group     分组
     * @param sessionId sessionId
     */
    public static void addCache(String group, String sessionId) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.hshPut(getCacheKey(group), sessionId, StrUtil.EMPTY);
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 刪除缓存
     *
     * @param group     分组
     * @param sessionId sessionId
     */
    public static void clearCache(String group, String sessionId) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.hshDelete(getCacheKey(group), sessionId);
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 查询缓存
     *
     * @param group 分组
     */
    public static List<String> getSessionIds(String group) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        Set<String> set;
        try {
            set = ObjectUtil.defaultIfNull(redisHelper.hshKeys(getCacheKey(group)), new HashSet<>());
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return new ArrayList<>(set);
    }
}
