package cn.cleanarch.gw.message.controller;

import cn.cleanarch.gw.common.websocket.helper.SocketMessageHandler;
import cn.cleanarch.gw.common.websocket.helper.SocketSendHelper;
import cn.cleanarch.gw.common.websocket.vo.MsgVO;
import cn.cleanarch.gw.common.websocket.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestWebSocketHandler extends SocketMessageHandler {

    @Autowired
    private SocketSendHelper socketSendHelper;

    @Override
    public void processMessage(MsgVO msg) {
        UserVO currentUser = getUserCache();
        socketSendHelper.sendByUser(msg.getUser(), msg.getKey(), "服务端已经处理:" + msg.getMessage());
    }
}
