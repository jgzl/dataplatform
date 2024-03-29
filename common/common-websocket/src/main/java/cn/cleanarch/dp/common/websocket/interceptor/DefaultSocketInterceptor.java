package cn.cleanarch.dp.common.websocket.interceptor;

import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;

/**
 * 默认拦截器
 *
 * @author li7hai26@outlook.com 2020/04/28 15:51
 */
@Component
public class DefaultSocketInterceptor implements SocketInterceptor {

    @Override
    public String processor() {
        return WebSocketConstant.DEFAULT_PROCESSOR;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
