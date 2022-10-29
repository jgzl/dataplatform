package cn.cleanarch.dp.message.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.websocket.helper.SocketSendHelper;
import cn.cleanarch.dp.common.websocket.vo.MsgVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * websocket推送控制器
 * @author li7hai26@outlook.com
 */
@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor
public class WebSocketController {

    private final SocketSendHelper socketSendHelper;

    /**
     * 推送给指定用户
     * @param msgVO
     * @return
     */
    @PostMapping(value = "/push/user")
    public R<Void> push2User(@RequestBody MsgVO msgVO) {
        socketSendHelper.sendByUser(msgVO);
        return R.success();
    }

    /**
     * 推送给所有用户(所有应用租户)
     * @param msgVO
     * @return
     */
    @PostMapping(value = "/push/all")
    public R<Void> push2All(@RequestBody MsgVO msgVO) {
        socketSendHelper.sendToAll(msgVO);
        return R.success();
    }
}
