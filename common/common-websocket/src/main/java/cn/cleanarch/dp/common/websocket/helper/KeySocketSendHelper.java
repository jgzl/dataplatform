package cn.cleanarch.dp.common.websocket.helper;

import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.dp.common.websocket.redis.BrokerListenRedis;
import cn.cleanarch.dp.common.websocket.redis.BrokerSessionRedis;
import cn.cleanarch.dp.common.websocket.redis.GroupSessionRedis;
import cn.cleanarch.dp.common.websocket.redis.SessionRedis;
import cn.cleanarch.dp.common.websocket.registry.BaseSessionRegistry;
import cn.cleanarch.dp.common.websocket.registry.GroupSessionRegistry;
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
 * 密钥连接websocket发送工具
 *
 * @author li7hai26@outlook.com 2018/11/05 15:16
 */
@Component
public class KeySocketSendHelper {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public KeySocketSendHelper(ObjectMapper objectMapper,
                               RedisTemplate<String, String> redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 指定sessionId发送webSocket消息
     *
     * @param sessionId sessionId
     * @param key       自定义的key
     * @param message   消息内容
     */
    public void sendBySession(String sessionId, String key, String message) {
        try {
            MsgVO msg = new MsgVO().setSessionId(sessionId).setKey(key).setMessage(message).setType(WebSocketConstant.SendType.S_SESSION).setBrokerId(BaseSessionRegistry.getBrokerId());
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            WebSocketSession clientSession = GroupSessionRegistry.getSession(sessionId);
            if (clientSession != null) {
                SocketSessionUtils.sendMsg(clientSession, sessionId, msgStr);
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
            MsgVO msg = new MsgVO().setSessionId(sessionId).setKey(key).setData(data).setType(WebSocketConstant.SendType.S_SESSION).setBrokerId(BaseSessionRegistry.getBrokerId());
            // 优先本地消费
            WebSocketSession clientSession = GroupSessionRegistry.getSession(sessionId);
            if (clientSession != null) {
                SocketSessionUtils.sendMsg(clientSession, sessionId, data);
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
     * 指定group发送webSocket消息
     *
     * @param group   分组
     * @param key     自定义的key
     * @param message 消息内容
     */
    public void sendByGroup(String group, String key, String message) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setGroup(group).setKey(key).setMessage(message).setType(WebSocketConstant.SendType.S_GROUP).setBrokerId(brokerId);
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            List<String> sessionIdList = GroupSessionRedis.getSessionIds(group);
            SocketSessionUtils.sendGroupMsg(sessionIdList, msgStr);
            // 通知其他服务
            notice(group, msgStr);
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 指定group发送webSocket消息
     *
     * @param group 分组
     * @param key   自定义的key
     * @param data  数据
     */
    public void sendByGroup(String group, String key, byte[] data) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setGroup(group).setKey(key).setData(data).setType(WebSocketConstant.SendType.S_GROUP).setBrokerId(brokerId);
            // 优先本地消费
            List<String> sessionIdList = GroupSessionRedis.getSessionIds(group);
            SocketSessionUtils.sendGroupMsg(sessionIdList, data);
            // 通知其他服务
            notice(group, objectMapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 向所有服务发送webSocket消息
     *
     * @param key     自定义的key
     * @param message 消息内容
     */
    public void sendToAll(String key, String message) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setKey(key).setMessage(message).setType(WebSocketConstant.SendType.S_ALL).setBrokerId(brokerId);
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            List<WebSocketSession> groupSessionList = GroupSessionRegistry.getAllSession();
            for (WebSocketSession item : groupSessionList) {
                SocketSessionUtils.sendMsg(item, msg.getSessionId(), msgStr);
            }
            // 通知远程
            redisTemplate.convertAndSend(WebSocketConstant.CHANNEL, msgStr);
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 向所有服务发送webSocket消息
     *
     * @param key  自定义的key
     * @param data 数据
     */
    public void sendToAll(String key, byte[] data) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setKey(key).setData(data).setType(WebSocketConstant.SendType.S_ALL).setBrokerId(brokerId);
            // 优先本地消费
            List<WebSocketSession> groupSessionList = GroupSessionRegistry.getAllSession();
            for (WebSocketSession item : groupSessionList) {
                SocketSessionUtils.sendMsg(item, msg.getSessionId(), data);
            }
            // 通知远程
            redisTemplate.convertAndSend(WebSocketConstant.CHANNEL, objectMapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 关闭分组的所有连接
     *
     * @param group 分组
     */
    public void closeSessionByGroup(String group) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = new MsgVO().setType(WebSocketConstant.SendType.CLOSE).setGroup(group).setBrokerId(brokerId);
            // 优先本地消费
            List<String> sessionIdList = GroupSessionRedis.getSessionIds(group);
            SocketSessionUtils.closeSession(sessionIdList);
            // 通知远程
            redisTemplate.convertAndSend(WebSocketConstant.CHANNEL, objectMapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 指定服务通道广播
     */
    private void notice(String group, String msgStr) {
        // 获取所有实例
        String brokerId = BaseSessionRegistry.getBrokerId();
        List<String> brokerList = BrokerListenRedis.getCache();
        List<String> groupSession = GroupSessionRedis.getSessionIds(group);
        brokerList.forEach(item -> {
            if (Objects.equals(item, brokerId)) {
                // 本节点不用处理
                return;
            }
            List<String> brokerSession = BrokerSessionRedis.getSessionIds(item);
            brokerSession.retainAll(groupSession);
            if (CollUtil.isNotEmpty(brokerSession)) {
                // 该节点有指定用户session信息，发送通知
                redisTemplate.convertAndSend(item, msgStr);
            }
        });
    }
}
