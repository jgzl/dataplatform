package cn.cleanarch.gw.common.websocket.handler;

import cn.cleanarch.gw.common.redis.RedisHelper;
import cn.cleanarch.gw.common.websocket.config.WebSocketConfig;
import cn.cleanarch.gw.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.gw.common.websocket.helper.SocketMessageHandler;
import cn.cleanarch.gw.common.websocket.redis.*;
import cn.cleanarch.gw.common.websocket.registry.GroupSessionRegistry;
import cn.cleanarch.gw.common.websocket.registry.UserSessionRegistry;
import cn.cleanarch.gw.common.websocket.vo.*;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 默认处理器
 *
 * @author shuangfei.zhu@hand-china.com 2020/04/28 16:46
 */
@Component
@RequiredArgsConstructor
public class DefaultSocketHandler implements SocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSocketHandler.class);

    private final WebSocketConfig config;
    private final ObjectMapper objectMapper;
    private final RedisHelper redisHelper;

    @Override
    public String processor() {
        return WebSocketConstant.DEFAULT_PROCESSOR;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            String sessionId = null;
            if (session instanceof StandardWebSocketSession) {
                // websocket连接方式
                sessionId = session.getId();
            } else if (session instanceof WebSocketServerSockJsSession) {
                // sock js 连接
                sessionId = ((WebSocketSession) FieldUtils.readField(session, "webSocketSession", true)).getId();
            } else {
                throw new RuntimeException("connection error");
            }
            Map<String, Object> attributeMap = session.getAttributes();
            if (attributeMap.containsKey(WebSocketConstant.Attributes.TOKEN)) {
                // 前端用户连接
                userConnection(session, sessionId);
            } else if (attributeMap.containsKey(WebSocketConstant.Attributes.SECRET_KEY) && attributeMap.containsKey(WebSocketConstant.Attributes.GROUP)) {
                // 密钥连接
                secretConnection(session, sessionId);
            } else {
                throw new RuntimeException("connection error");
            }

        } catch (Exception e) {
            try {
                session.close();
            } catch (IOException ex) {
                logger.debug("session : {} closed failed.", session);
            }
            logger.debug("webSocket connection failed. message : {}, token : {}", e.getMessage(), session.getAttributes().get(WebSocketConstant.Attributes.TOKEN));
        }
    }


    /**
     * 密钥链接
     *
     * @param webSocketSession WebSocketSession
     * @param sessionId        WebSocketSessionId
     */
    private void secretConnection(WebSocketSession webSocketSession, String sessionId) {
        String group = String.valueOf(webSocketSession.getAttributes().get(WebSocketConstant.Attributes.GROUP));
        // 内存中存储webSocketSession
        GroupSessionRegistry.addSession(webSocketSession, sessionId);
        String brokerId = GroupSessionRegistry.getBrokerId();
        SessionVO session = new SessionVO(sessionId, group, brokerId);
        // 记录session
        SessionRedis.addCache(session);
        // 记录分组session
        GroupSessionRedis.addCache(group, sessionId);
        // 记录节点session
        BrokerSessionRedis.addCache(brokerId, sessionId);
    }

    /**
     * 用户连接
     *
     * @param webSocketSession WebSocketSession
     * @param sessionId        WebSocketSessionId
     */
    private void userConnection(WebSocketSession webSocketSession, String sessionId) {
        // 获取用户信息
        String token = String.valueOf(webSocketSession.getAttributes().get(WebSocketConstant.Attributes.TOKEN));
        UserVO userVO = getAuthentication(token);
        String tenant = userVO.getTenant();
        String user = userVO.getUser();
        String role = userVO.getRole();
        logger.info("websocket connection success. user : {}", user);
        // 内存中存储webSocketSession
        UserSessionRegistry.addSession(webSocketSession, sessionId);
        String brokerId = UserSessionRegistry.getBrokerId();
        SessionVO session = new SessionVO(sessionId, user, tenant, role, token, brokerId);
        // 记录session
        SessionRedis.addCache(session);
        // 记录在线用户
        OnlineUserRedis.refreshCache(session);
        // 记录用户session
        UserSessionRedis.addCache(user, sessionId);
        // 记录节点session
        BrokerSessionRedis.addCache(brokerId, sessionId);
    }

    private UserVO getAuthentication(String token) {
        String userVoJson = redisHelper.hshGet(WebSocketConstant.ACCESS_TOKEN, token);
        if (StrUtil.isBlank(userVoJson)) {
            throw new RuntimeException("User authentication failed");
        }
        UserVO userVO = redisHelper.fromJson(userVoJson,UserVO.class);
        if (userVO == null) {
            throw new RuntimeException("User authentication failed");
        }
        return userVO;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messageStr = message.getPayload();
        // 心跳
        if (Objects.equals(messageStr, config.getHeartbeat())) {
            logger.info("websocket心跳");
            return;
        }
        Map<String, SocketMessageHandler> beansOfType = SpringUtil.getBeansOfType(SocketMessageHandler.class);
        beansOfType.forEach((name, bean) -> {
            if (bean.needPrincipal() && session.getAttributes().containsKey(WebSocketConstant.Attributes.TOKEN)) {
                String token = String.valueOf(session.getAttributes().get(WebSocketConstant.Attributes.TOKEN));
                bean.setUserCache(getAuthentication(token));
            }
            MsgVO msg;
            try {
                msg = objectMapper.readValue(messageStr, MsgVO.class);
                bean.processMessage(msg);
            } catch (Exception e) {
                logger.error("handleMessage error , message : {}", message.getPayload());
            } finally {
                bean.clearUserCache();
            }
        });
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        byte[] data = message.getPayload().array();
        Map<String, SocketMessageHandler> beansOfType = SpringUtil.getBeansOfType(SocketMessageHandler.class);
        beansOfType.forEach((name, bean) -> {
            if (bean.needPrincipal() && session.getAttributes().containsKey(WebSocketConstant.Attributes.TOKEN)) {
                String token = String.valueOf(session.getAttributes().get(WebSocketConstant.Attributes.TOKEN));
                bean.setUserCache(getAuthentication(token));
            }
            try {
                bean.processByte(session.getAttributes(), data);
            } catch (Exception e) {
                logger.error("handleMessage error , message : {}", message.getPayload());
            } finally {
                bean.clearUserCache();
            }
        });
    }

    @Override
    public void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        // TODO 待添加支持
        logger.info("Unexpected WebSocket message type: {}", message);
    }
}
