package cn.cleanarch.dp.message.controller;

import cn.cleanarch.dp.common.websocket.helper.SocketMessageHandler;
import cn.cleanarch.dp.common.websocket.helper.SocketSendHelper;
import cn.cleanarch.dp.common.websocket.vo.MsgVO;
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
        socketSendHelper.sendByUser(msg.getUser(), msg.getKey(), "服务端已经处理:" + msg.getMessage());
    }
}
