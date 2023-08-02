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
import cn.cleanarch.dp.common.websocket.vo.UserVO;
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
 * @author li7hai26@outlook.com 2018/11/05 15:16
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
     * @param msg msg对象
     */
    public void sendBySession(MsgVO msg) {
        try {
            String sessionId = msg.getSessionId();
            msg.setType(WebSocketConstant.SendType.SESSION).setBrokerId(BaseSessionRegistry.getBrokerId());
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            WebSocketSession webSocketSession = UserSessionRegistry.getSession(sessionId);
            if (webSocketSession != null) {
                if (msg.getData() != null) {
                    SocketSessionUtils.sendMsg(webSocketSession, sessionId, msg.getData());
                } else {
                    SocketSessionUtils.sendMsg(webSocketSession, sessionId, msgStr);
                }
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
     * 指定用户发送webSocket消息
     *
     * @param msg msg对象
     */
    public void sendByUser(MsgVO msg) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            msg.setType(WebSocketConstant.SendType.USER).setBrokerId(brokerId);
            UserVO userVO = msg.toUserVO();
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            List<String> sessionIdList = UserSessionRedis.getSessionIds(userVO);
            if (msg.getData() != null) {
                SocketSessionUtils.sendUserMsg(sessionIdList, msg.getData());
            } else {
                SocketSessionUtils.sendUserMsg(sessionIdList, msgStr);
            }
            // 通知其他服务
            notice(userVO, msgStr);
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 向所有用户发送webSocket消息
     *
     * @param msg msg对象
     */
    public void sendToAll(MsgVO msg) {
        try {
            String brokerId = BaseSessionRegistry.getBrokerId();
            msg.setType(WebSocketConstant.SendType.ALL).setBrokerId(brokerId);
            String msgStr = objectMapper.writeValueAsString(msg);
            // 优先本地消费
            List<WebSocketSession> userSessionList = UserSessionRegistry.getAllSession();
            for (WebSocketSession session : userSessionList) {
                if (msg.getData() != null) {
                    SocketSessionUtils.sendMsg(session, msg.getSessionId(), msg.getData());
                } else {
                    SocketSessionUtils.sendMsg(session, msg.getSessionId(), msgStr);
                }
            }
            // 通知其他服务
            redisTemplate.convertAndSend(WebSocketConstant.CHANNEL, msgStr);
        } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * 指定服务通道广播
     */
    private void notice(UserVO user, String msgStr) {
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
