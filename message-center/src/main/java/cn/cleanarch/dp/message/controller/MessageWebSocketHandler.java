package cn.cleanarch.dp.message.controller;

import cn.cleanarch.dp.common.websocket.helper.SocketMessageHandler;
import cn.cleanarch.dp.common.websocket.helper.SocketSendHelper;
import cn.cleanarch.dp.common.websocket.vo.MsgVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author li7hai26@outlook.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageWebSocketHandler extends SocketMessageHandler {

    private final SocketSendHelper socketSendHelper;

    @Override
    public void processMessage(MsgVO msg) {
        socketSendHelper.sendByUser(msg);
    }
}
