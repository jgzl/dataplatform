package cn.cleanarch.dp.common.websocket.listener;

import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.dp.common.websocket.redis.GroupSessionRedis;
import cn.cleanarch.dp.common.websocket.redis.UserSessionRedis;
import cn.cleanarch.dp.common.websocket.registry.BaseSessionRegistry;
import cn.cleanarch.dp.common.websocket.registry.GroupSessionRegistry;
import cn.cleanarch.dp.common.websocket.registry.UserSessionRegistry;
import cn.cleanarch.dp.common.websocket.util.SocketSessionUtils;
import cn.cleanarch.dp.common.websocket.vo.MsgVO;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * redis 通道消息监听
 *
 * @author shuangfei.zhu@hand-china.com 2019/04/19 15:52
 */
@Component
public class SocketMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(SocketMessageListener.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public SocketMessageListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void messageListener(String msgVO) {
        try {
            // 监听到消息发送webSocket消息
            String brokerId = BaseSessionRegistry.getBrokerId();
            MsgVO msg = objectMapper.readValue(msgVO, MsgVO.class);
            if (Objects.equals(brokerId, msg.getBrokerId())) {
                // 消息发送方为本服务，不处理
                return;
            }
            List<String> sessionIdList;
            byte[] data = msg.getData();
            String user = msg.getUser();
            String group = msg.getGroup();
            switch (msg.getType()) {
                case WebSocketConstant.SendType.SESSION:
                    WebSocketSession session = UserSessionRegistry.getSession(msg.getSessionId());
                    if (data != null) {
                        SocketSessionUtils.sendMsg(session, msg.getSessionId(), data);
                    } else {
                        SocketSessionUtils.sendMsg(session, msg.getSessionId(), objectMapper.writeValueAsString(msg));
                    }
                    break;
                case WebSocketConstant.SendType.USER:
                    sessionIdList = UserSessionRedis.getSessionIds(user);
                    if (data != null) {
                        SocketSessionUtils.sendUserMsg(sessionIdList, data);
                    } else {
                        SocketSessionUtils.sendUserMsg(sessionIdList, objectMapper.writeValueAsString(msg));
                    }
                    break;
                case WebSocketConstant.SendType.ALL:
                    List<WebSocketSession> userSessionList = UserSessionRegistry.getAllSession();
                    for (WebSocketSession item : userSessionList) {
                        if (data != null) {
                            SocketSessionUtils.sendMsg(item, msg.getSessionId(), data);
                        } else {
                            SocketSessionUtils.sendMsg(item, msg.getSessionId(), objectMapper.writeValueAsString(msg));
                        }
                    }
                    break;
                case WebSocketConstant.SendType.S_SESSION:
                    WebSocketSession clientSession = GroupSessionRegistry.getSession(msg.getSessionId());
                    if (data != null) {
                        SocketSessionUtils.sendMsg(clientSession, msg.getSessionId(), data);
                    } else {
                        SocketSessionUtils.sendMsg(clientSession, msg.getSessionId(), objectMapper.writeValueAsString(msg));
                    }
                    break;
                case WebSocketConstant.SendType.S_GROUP:
                    sessionIdList = GroupSessionRedis.getSessionIds(group);
                    if (data != null) {
                        SocketSessionUtils.sendGroupMsg(sessionIdList, data);
                    } else {
                        SocketSessionUtils.sendGroupMsg(sessionIdList, objectMapper.writeValueAsString(msg));
                    }
                    break;
                case WebSocketConstant.SendType.S_ALL:
                    List<WebSocketSession> groupSessionList = GroupSessionRegistry.getAllSession();
                    for (WebSocketSession item : groupSessionList) {
                        if (data != null) {
                            SocketSessionUtils.sendMsg(item, msg.getSessionId(), data);
                        } else {
                            SocketSessionUtils.sendMsg(item, msg.getSessionId(), objectMapper.writeValueAsString(msg));
                        }
                    }
                    break;
                case WebSocketConstant.SendType.CLOSE:
                    if (user != null) {
                        // 关闭用户连接
                        sessionIdList = UserSessionRedis.getSessionIds(user);
                        SocketSessionUtils.closeSession(sessionIdList);
                    }
                    if (StrUtil.isNotBlank(group)) {
                        // 关闭分组连接
                        sessionIdList = GroupSessionRedis.getSessionIds(group);
                        SocketSessionUtils.closeSession(sessionIdList);
                    }
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }
}
