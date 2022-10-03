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
 * 存储用户与用户所属的sessionId
 *
 * @author shuangfei.zhu@hand-china.com 2020/09/01 20:49
 */
public class UserSessionRedis extends WebSocketRedis {

    /**
     * 生成redis存储key
     *
     * @param user 用户Id
     * @return key
     */
    private static String getCacheKey(String user) {
        return WebSocketConstant.REDIS_KEY + "user-sessions:" + user;
    }

    /**
     * 添加缓存
     *
     * @param user    用户Id
     * @param sessionId sessionId
     */
    public static void addCache(String user, String sessionId) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.hshPut(getCacheKey(user), sessionId, StrUtil.EMPTY);
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 刪除缓存
     *
     * @param user    用户Id
     * @param sessionId sessionId
     */
    public static void clearCache(String user, String sessionId) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.hshDelete(getCacheKey(user), sessionId);
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 查询缓存
     *
     * @param user 用户Id
     */
    public static List<String> getSessionIds(String user) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        Set<String> set;
        try {
            set = ObjectUtil.defaultIfNull(redisHelper.hshKeys(getCacheKey(user)), new HashSet<>());
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return new ArrayList<>(set);
    }
}
