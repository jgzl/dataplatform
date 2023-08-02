package cn.cleanarch.dp.common.websocket.init;

import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.dp.common.websocket.listener.SocketMessageListener;
import cn.cleanarch.dp.common.websocket.redis.*;
import cn.cleanarch.dp.common.websocket.registry.BaseSessionRegistry;
import cn.cleanarch.dp.common.websocket.registry.UserSessionRegistry;
import cn.cleanarch.dp.common.websocket.vo.SessionVO;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 初始化心跳注册
 *
 * @author li7hai26@outlook.com 2019/05/30 15:30
 */
@Component
public class ClientInit implements CommandLineRunner {

    @Autowired
    @Qualifier("ws-container")
    private RedisMessageListenerContainer container;
    @Autowired
    private SocketMessageListener listener;
    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Autowired
    @Qualifier("websocket-check-executor")
    private AsyncTaskExecutor taskExecutor;

    private static final Logger logger = LoggerFactory.getLogger(ClientInit.class);

    @Override
    public void run(String... args) throws Exception {

        // 创建定时线程
        ScheduledExecutorService scheduledExecutorService =
                new ScheduledThreadPoolExecutor(5, new BasicThreadFactory.Builder()
                        .namingPattern("websocket-client-register")
                        .daemon(true)
                        .build());
        scheduledExecutorService.scheduleWithFixedDelay(new ClientRegister(), 0, 10, TimeUnit.SECONDS);
        // 在线用户自检线程
        scheduledExecutorService.scheduleWithFixedDelay(new Check(), 0, 600, TimeUnit.SECONDS);
    }

    class ClientRegister implements Runnable {

        @Override
        public void run() {
            try {
                String brokerId = BaseSessionRegistry.getBrokerId();
                // 刷新心跳缓存
                BrokerRedis.refreshCache(brokerId);
                BrokerListenRedis.refreshCache(brokerId);

                // 下面的检查逻辑，异步执行，防止执行时间超过5秒导致当前客户端下线
                taskExecutor.execute(() -> {
                    try {
                        // 检查其他客户端状态
                        List<String> brokerList = BrokerListenRedis.getCache();
                        for (String item : brokerList) {
                            if (BrokerRedis.isAlive(item)) {
                                continue;
                            }
                            BrokerListenRedis.clearRedisCache(item);
                            List<String> sessionIds = BrokerSessionRedis.getSessionIds(item);
                            // 清除节点
                            BrokerSessionRedis.clearCache(item);
                            for (String sessionId : sessionIds) {
                                SessionVO session = SessionRedis.getSession(sessionId);
                                SessionRedis.clearCache(sessionId);
                                if (session == null) {
                                    continue;
                                }
                                String user = session.getUser();
                                if (user != null) {
                                    // 清除在线用户
                                    OnlineUserRedis.deleteCache(session);
                                    // 清理用户session
                                    UserSessionRedis.clearCache(session);
                                }
                                String group = session.getGroup();
                                if (StrUtil.isNotBlank(group)) {
                                    // 清理group的session
                                    GroupSessionRedis.clearCache(group, sessionId);
                                }
                            }
                        }
                        // 检查channel监听是否有效
                        logger.debug("websocket container running: {}", container.isRunning());
                        if (!container.isRunning()) {
                            logger.info("websocket container Reinitialize......");
                            container = new RedisMessageListenerContainer();
                            container.setConnectionFactory(connectionFactory);
                            container.addMessageListener(new MessageListenerAdapter(listener, "messageListener"), new PatternTopic(WebSocketConstant.CHANNEL));
                            container.addMessageListener(new MessageListenerAdapter(listener, "messageListener"), new PatternTopic(BaseSessionRegistry.getBrokerId()));
                        }
                    } catch (Exception e) {
                        logger.error("exception:", e);
                    }
                });
            } catch (Exception e) {
                logger.warn("websocket register error!", e);
            }
        }
    }

    static class Check implements Runnable {

        private final RedisHelper redisHelper = SpringUtil.getBean(RedisHelper.class);

        @Override
        public void run() {
            try {
                String brokerId = UserSessionRegistry.getBrokerId();
                List<String> liveBrokerList = BrokerListenRedis.getCache();
                // 分批查询
                int page = 0;
                int size = 500;
                List<SessionVO> sessionList;
                do {
                    sessionList = OnlineUserRedis.getCache(page, size);
                    for (SessionVO session : sessionList) {
                        String sessionId = session.getSessionId();
                        // 所属客户端已下线
                        if (StrUtil.isBlank(session.getBrokerId()) || !liveBrokerList.contains(session.getBrokerId())) {
                            SessionRedis.clearCache(sessionId);
                            // 清除在线用户
                            OnlineUserRedis.deleteCache(session);
                            // 清除用户session
                            UserSessionRedis.clearCache(session);
                            // 清理节点session
                            BrokerSessionRedis.clearCache(session.getBrokerId(), sessionId);
                            continue;
                        }
                        // 只处理本客户端的
                        if (!Objects.equals(session.getBrokerId(), brokerId)) {
                            continue;
                        }
                        // access_token已失效
                        if (checkAccessToken(session.getAccessToken())) {
                            clear(session);
                            continue;
                        }
                        WebSocketSession webSocketSession = UserSessionRegistry.getSession(sessionId);
                        // websocket已失效
                        if (webSocketSession == null || !webSocketSession.isOpen()) {
                            clear(session);
                        }
                    }
                    page++;
                } while (sessionList.size() >= size);
            } catch (Exception e) {
                logger.warn("online user check error!", e);
            }
        }

        private void clear(SessionVO session) {
            String sessionId = session.getSessionId();
            SessionRedis.clearCache(sessionId);
            // 清除在线用户
            OnlineUserRedis.deleteCache(session);
            // 清除用户session
            UserSessionRedis.clearCache(session);
            // 清理节点session
            BrokerSessionRedis.clearCache(session.getBrokerId(), sessionId);
            // 清理内存
            UserSessionRegistry.removeSession(sessionId);
        }

        private boolean checkAccessToken(String accessToken) {
            boolean unable = !redisHelper.hshHasKey(WebSocketConstant.ACCESS_TOKEN, accessToken);
            redisHelper.clearCurrentDatabase();
            return unable;
        }
    }
}
