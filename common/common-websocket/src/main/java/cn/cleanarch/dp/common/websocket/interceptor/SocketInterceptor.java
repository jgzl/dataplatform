package cn.cleanarch.dp.common.websocket.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;

/**
 * 握手拦截器
 *
 * @author li7hai26@outlook.com 2020/04/28 15:44
 */
public interface SocketInterceptor {

    /**
     * 执行的processor
     *
     * @return processor
     */
    String processor();

    /**
     * 握手前调用
     *
     * @param request    request
     * @param response   response
     * @param wsHandler  wsHandler
     * @param attributes attributes
     * @return 握手是否成功
     * @throws Exception exception
     */
    boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception;

    /**
     * 握手后调用
     *
     * @param request   request
     * @param response  response
     * @param wsHandler wsHandler
     * @param exception exception
     */
    void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception);
}
