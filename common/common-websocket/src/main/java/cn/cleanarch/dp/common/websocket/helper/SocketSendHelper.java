package cn.cleanarch.dp.common.websocket.helper;

import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.dp.common.websocket.redis.BrokerListenRedis;
import cn.cleanarch.dp.common.websocket.redis.BrokerSessionRedis;
import cn.cleanarch.dp.common.websocket.redis.SessionRedis;
import cn.cleanarch.dp.common.websocket.redis.UserSessionRedis;
import cn.cleanarch.dp.common.websocket.registry.BaseSessionRegistry;
import cn.cleanarch.dp.common.websocket.registry.UserSessionRegistry;
import cn.cleanarch.dp.common.websocket.util.SocketSessionUtils;
import cn.cleanarch.dp.common.websocket.vo.MsgVO;
import cn.cleanarch.dp.common.websocket.vo.SessionVO;
import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Objects;

/**
 * token连接webSocket发送工具
 *
 * @author shuangfei.zhu@hand-china.com 2018/11/05 15:16
 */
@Component
public class SocketSendHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 指定sessionId发送webSocket消息
     *
     * @param sessionId sessionId
     * @param key       自定义的key
     * @param message   消息内容
     */
    public void sendBySession(String sessionId, String key, String message) {
        try {
            MsgVO msg = new MsgVO().setSessionId(sessionId).setKey(key).setMessage(message).setType(WebSocketConstant.SendType.SESSION).setBrokerId(BaseSessionRegistry.getBrokerId());
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            WebSocketSession webSocketSession = UserSessionRegistry.getSession(sessionId);
            if (webSocketSession != null) {
                SocketSessionUtils.sendMsg(webSocketSession, sessionId, msgStr);
                return;
            }
            SessionVO session = SessionRedis.getSession(sessionId);
            if (session == null) {
                return;
            }
            // 通知目标服务
            redisTemplate.convertAndSend(session.getBrokerId(), msgStr);
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 指定sessionId发送webSocket消息
     *
     * @param sessionId sessionId
     * @param key       自定义的key
     * @param data      数据
     */
    public void sendBySession(String sessionId, String key, byte[] data) {
        try {
            MsgVO msg = new MsgVO().setSessionId(sessionId).setKey(key).setData(data).setType(WebSocketConstant.SendType.SESSION).setBrokerId(BaseSessionRegistry.getBrokerId());
            // 优先本地消费
            WebSocketSession webSocketSession = UserSessionRegistry.getSession(sessionId);
            if (webSocketSession != null) {
                SocketSessionUtils.sendMsg(webSocketSession, sessionId, data);
                return;
            }
            SessionVO session = SessionRedis.getSession(sessionId);
            if (session == null) {
                return;
            }
            // 通知目标服务
            redisTemplate.convertAndSend(session.getBrokerId(), objectMapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 指定用户发送webSocket消息
     *
     * @param user  用户Id
     * @param key     自定义的key
     * @param message 消息内容
     */
    public void sendByUser(String user, String key, String message) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setUser(user).setKey(key).setMessage(message).setType(WebSocketConstant.SendType.USER).setBrokerId(brokerId);
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            List<String> sessionIdList = UserSessionRedis.getSessionIds(user);
            SocketSessionUtils.sendUserMsg(sessionIdList, msgStr);
            // 通知其他服务
            notice(user, msgStr);
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 指定用户发送webSocket消息
     *
     * @param user 用户Id
     * @param key    自定义的key
     * @param data   数据
     */
    public void sendByUser(String user, String key, byte[] data) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setUser(user).setKey(key).setData(data).setType(WebSocketConstant.SendType.USER).setBrokerId(brokerId);
            // 优先本地消费
            List<String> sessionIdList = UserSessionRedis.getSessionIds(user);
            SocketSessionUtils.sendUserMsg(sessionIdList, data);
            // 通知其他服务
            notice(user, objectMapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 向所有用户发送webSocket消息
     *
     * @param key     自定义的key
     * @param message 消息内容
     */
    public void sendToAll(String key, String message) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setKey(key).setMessage(message).setType(WebSocketConstant.SendType.ALL).setBrokerId(brokerId);
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            List<WebSocketSession> userSessionList = UserSessionRegistry.getAllSession();
            for (WebSocketSession item : userSessionList) {
                SocketSessionUtils.sendMsg(item, msg.getSessionId(), msgStr);
            }
            // 通知其他服务
            redisTemplate.convertAndSend(WebSocketConstant.CHANNEL, msgStr);
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 向所有用户发送webSocket消息
     *
     * @param key  自定义的key
     * @param data 数据
     */
    public void sendToAll(String key, byte[] data) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setKey(key).setData(data).setType(WebSocketConstant.SendType.ALL).setBrokerId(brokerId);
            // 优先本地消费
            List<WebSocketSession> userSessionList = UserSessionRegistry.getAllSession();
            for (WebSocketSession item : userSessionList) {
                SocketSessionUtils.sendMsg(item, msg.getSessionId(), data);
            }
            // 通知其他服务
            redisTemplate.convertAndSend(WebSocketConstant.CHANNEL, objectMapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 指定服务通道广播
     */
    private void notice(String user, String msgStr) {
        // 获取所有实例
        String brokerId = BaseSessionRegistry.getBrokerId();
        List<String> brokerList = BrokerListenRedis.getCache();
        List<String> userSession = UserSessionRedis.getSessionIds(user);
        brokerList.forEach(item -> {
            if (Objects.equals(item, brokerId)) {
                // 本节点不用处理
                return;
            }
            List<String> brokerSession = BrokerSessionRedis.getSessionIds(item);
            brokerSession.retainAll(userSession);
            if (CollUtil.isNotEmpty(brokerSession)) {
                // 该节点有指定用户session信息，发送通知
                redisTemplate.convertAndSend(item, msgStr);
            }
        });
    }
}
