package cn.cleanarch.dp.common.websocket.redis;

import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.dp.common.websocket.vo.SessionVO;
import cn.hutool.core.util.StrUtil;

/**
 * 自定义session存储
 *
 * @author li7hai26@outlook.com 2020/09/01 20:02
 */
public class SessionRedis extends WebSocketRedis {

    /**
     * 生成redis存储key
     *
     * @param sessionId webSocket的sessionId
     * @return key
     */
    private static String getCacheKey(String sessionId) {
        return WebSocketConstant.REDIS_KEY + "sessions:" + sessionId;
    }

    /**
     * 添加缓存
     *
     * @param session session信息
     */
    public static void addCache(SessionVO session) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.strSet(getCacheKey(session.getSessionId()), redisHelper.toJson(session));
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 刪除缓存
     *
     * @param sessionId webSocketSessionId
     */
    public static void clearCache(String sessionId) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.delKey(getCacheKey(sessionId));
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 查询缓存
     *
     * @param sessionId webSocketSessionId
     */
    public static SessionVO getSession(String sessionId) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        SessionVO session = null;
        try {
            String result = redisHelper.strGet(getCacheKey(sessionId));
            if (StrUtil.isNotBlank(result)) {
                session = redisHelper.fromJson(result, SessionVO.class);
            }
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return session;
    }
}
