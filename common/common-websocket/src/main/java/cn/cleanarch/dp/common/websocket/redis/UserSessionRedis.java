package cn.cleanarch.dp.common.websocket.redis;

import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.dp.common.websocket.vo.SessionVO;
import cn.cleanarch.dp.common.websocket.vo.UserVO;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 存储用户与用户所属的sessionId
 *
 * @author li7hai26@outlook.com 2020/09/01 20:49
 */
public class UserSessionRedis extends WebSocketRedis {

    /**
     * 生成redis存储key
     *
     * @param user 用户Id
     * @return key
     */
    private static String getCacheKey(UserVO user) {
        return WebSocketConstant.REDIS_KEY + "user-sessions:" + user.getUser() + ":app:" + user.getApp() + ":tenant:" + user.getTenant();
    }

    /**
     * 添加缓存
     *
     * @param session session对象
     */
    public static void addCache(SessionVO session) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.hshPut(getCacheKey(session.toUserVO()), session.getSessionId(), StrUtil.EMPTY);
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 刪除缓存
     *
     * @param session session对象
     */
    public static void clearCache(SessionVO session) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.hshDelete(getCacheKey(session.toUserVO()), session.getSessionId());
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 查询缓存
     *
     * @param userVO 用户Id
     */
    public static List<String> getSessionIds(UserVO userVO) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        Set<String> set;
        try {
            set = ObjectUtil.defaultIfNull(redisHelper.hshKeys(getCacheKey(userVO)), new HashSet<>());
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return new ArrayList<>(set);
    }
}
