package cn.cleanarch.dp.common.websocket.redis;

import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.dp.common.websocket.vo.SessionVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 在线用户缓存工具
 *
 * @author li7hai26@outlook.com 2019/10/11 20:19
 */
public class OnlineUserRedis extends WebSocketRedis {

    /**
     * 生成redis存储key
     *
     * @return key
     */
    private static String getCacheKey() {
        return WebSocketConstant.REDIS_KEY + "online-users:all";
    }

    /**
     * 生成redis存储key
     *
     * @return key
     */
    private static String getCacheKey(String app,String tenant) {
        return WebSocketConstant.REDIS_KEY + "online-users:app:" + app+":tenant:" + tenant;
    }

    /**
     * 刷新缓存
     *
     * @param session session信息
     */
    public static void refreshCache(SessionVO session) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            // 缓存记录sessionId 与用户的关系，还要考虑分页排序
            long date = System.nanoTime();
            redisHelper.zSetAdd(getCacheKey(), session.getSessionId(), date);
            redisHelper.zSetAdd(getCacheKey(session.getApp(), session.getTenant()), session.getSessionId(), date);
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 刪除缓存
     *
     * @param session session信息
     */
    public static void deleteCache(SessionVO session) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            redisHelper.zSetRemove(getCacheKey(), session.getSessionId());
            redisHelper.zSetRemove(getCacheKey(session.getApp(), session.getTenant()), session.getSessionId());
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 刪除缓存
     *
     * @param sessionList session信息
     */
    public static void deleteCache(List<SessionVO> sessionList) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        try {
            for (SessionVO session : sessionList) {
                redisHelper.zSetRemove(getCacheKey(), session.getSessionId());
                redisHelper.zSetRemove(getCacheKey(session.getApp(), session.getTenant()), session.getSessionId());
            }
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 获取在线人数
     */
    public static Long getSize() {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        Long total;
        try {
            total = redisHelper.zSetSize(getCacheKey());
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return total;
    }

    /**
     * 指定租户获取在线人数
     */
    public static Long getSize(String app,String tenant) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        Long total;
        try {
            total = redisHelper.zSetSize(getCacheKey(app, tenant));
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return total;
    }

    /**
     * 分页查询在线用户
     *
     * @param page 页
     * @param size 每页数量
     */
    public static List<SessionVO> getCache(int page, int size) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        List<SessionVO> list = new ArrayList<>();
        try {
            int start = size * page;
            int end = start + size - 1;
            Set<String> keySets = redisHelper.zSetRange(getCacheKey(), (long) start, (long) end);
            keySets.forEach(item -> {
                SessionVO session = SessionRedis.getSession(item);
                if (session != null && session.getUser() != null) {
                    list.add(session);
                }
            });
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return list;
    }

    /**
     * 分页查询在线用户
     *
     * @param page 页
     * @param size 每页数量
     */
    public static List<SessionVO> getCache(int page, int size, String app, String tenant) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        List<SessionVO> list = new ArrayList<>();
        try {
            int start = size * page;
            int end = start + size - 1;
            Set<String> keySets = redisHelper.zSetRange(getCacheKey(app, tenant), (long) start, (long) end);
            keySets.forEach(item -> {
                SessionVO session = SessionRedis.getSession(item);
                if (session != null && session.getUser() != null) {
                    list.add(session);
                }
            });
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return list;
    }

    /**
     * 查询所有在线用户
     */
    public static List<SessionVO> getCache() {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        List<SessionVO> list = new ArrayList<>();
        try {
            Long count = redisHelper.zSetSize(getCacheKey());
            Set<String> keySets = redisHelper.zSetRange(getCacheKey(), 0L, count);
            keySets.forEach(item -> {
                SessionVO session = SessionRedis.getSession(item);
                if (session != null && session.getUser() != null) {
                    list.add(session);
                }
            });
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return list;
    }

    /**
     * 指定租户查询所有在线用户
     */
    public static List<SessionVO> getCache(String app, String tenant) {
        RedisHelper redisHelper = getRedisHelper();
        redisHelper.setCurrentDatabase(getConfig().getRedisDb());
        List<SessionVO> list = new ArrayList<>();
        try {
            Long count = redisHelper.zSetSize(getCacheKey());
            Set<String> keySets = redisHelper.zSetRange(getCacheKey(app, tenant), 0L, count);
            keySets.forEach(item -> {
                SessionVO session = SessionRedis.getSession(item);
                if (session != null && session.getUser() != null) {
                    list.add(session);
                }
            });
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return list;
    }
}
