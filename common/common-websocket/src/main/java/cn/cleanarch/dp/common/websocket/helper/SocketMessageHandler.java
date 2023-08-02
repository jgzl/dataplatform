package cn.cleanarch.dp.common.websocket.helper;

import cn.cleanarch.dp.common.websocket.vo.MsgVO;
import cn.cleanarch.dp.common.websocket.vo.UserVO;

import java.util.Map;

/**
 * 前端webSocket消息处理
 *
 * @author li7hai26@outlook.com 2018/12/27 16:25
 */
public abstract class SocketMessageHandler {


    private final ThreadLocal<UserVO> userCache = new ThreadLocal<>();

    /**
     * 处理接收到的消息
     *
     * @param msg 消息内容
     */
    public void processMessage(MsgVO msg) {
    }

    /**
     * 处理接收到的二进制数据
     *
     * @param data data
     */
    public void processByte(Map<String, Object> args, byte[] data) {
    }

    public boolean needPrincipal() {
        return false;
    }

    public UserVO getUserCache() {
        return userCache.get();
    }

    public void setUserCache(UserVO userCache) {
        this.userCache.set(userCache);
    }

    public void clearUserCache() {
        this.userCache.remove();
    }
}
