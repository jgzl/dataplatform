package cn.cleanarch.dp.common.websocket.registry;

import cn.cleanarch.dp.common.websocket.redis.*;
import cn.cleanarch.dp.common.websocket.vo.SessionVO;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

/**
 * description
 *
 * @author shuangfei.zhu@hand-china.com 2020/04/22 10:55
 */
public abstract class BaseSessionRegistry {

    protected BaseSessionRegistry() {
    }

    private static final String BROKER_ID = IdUtil.fastUUID();

    public static String getBrokerId() {
        return BROKER_ID;
    }

    /**
     * 清理session内存及缓存
     *
     * @param sessionId sessionId
     */
    public static void clearSession(String sessionId) {
        SessionVO session = SessionRedis.getSession(sessionId);
        SessionRedis.clearCache(sessionId);
        if (session == null) {
            return;
        }
        // 清理服务节点session
        BrokerSessionRedis.clearCache(session.getBrokerId(), sessionId);
        String user = session.getUser();
        if (user != null) {
            // 清除在线用户
            OnlineUserRedis.deleteCache(session);
            // 清理用户session
            UserSessionRedis.clearCache(session);
            // 清理内存
            UserSessionRegistry.removeSession(sessionId);
        }
        String group = session.getGroup();
        if (StrUtil.isNotBlank(group)) {
            // 清理group的session
            GroupSessionRedis.clearCache(group, sessionId);
            // 清理内存
            GroupSessionRegistry.removeSession(sessionId);
        }
    }
}
