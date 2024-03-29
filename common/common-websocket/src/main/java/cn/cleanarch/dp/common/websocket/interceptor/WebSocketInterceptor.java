package cn.cleanarch.dp.common.websocket.interceptor;

import cn.cleanarch.dp.common.core.constant.TokenConstants;
import cn.cleanarch.dp.common.websocket.config.WebSocketProperties;
import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * websocket拦截器
 *
 * @author li7hai26@outlook.com 2019/04/19 10:58
 */
public class WebSocketInterceptor implements HandshakeInterceptor {

    private static final String TOKEN_HEADER = TokenConstants.TOKEN_HEADER_PREFIX;
    private static final String TOKEN_HEADER_PREFIX = TokenConstants.TOKEN_HEADER_PREFIX;

    private final Map<String, SocketInterceptor> interceptors = new HashMap<>();

    private Map<String, SocketInterceptor> getInterceptors() {
        if (interceptors.size() > 0) {
            return interceptors;
        }
        Map<String, SocketInterceptor> map = SpringUtil.getBeansOfType(SocketInterceptor.class);
        for (SocketInterceptor interceptor : map.values()) {
            interceptors.put(interceptor.processor(), interceptor);
        }
        return interceptors;
    }

    /**
     * handler处理前调用,attributes属性最终在WebSocketSession里,可能通过webSocketSession.getAttributes().get(key值)获得
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("握手开始");
        if (request instanceof ServletServerHttpRequest serverHttpRequest) {
            boolean result = true;
            // 将所有路径参数添加到attributes
            for (Map.Entry<String, String[]> entry : serverHttpRequest.getServletRequest().getParameterMap().entrySet()) {
                attributes.put(entry.getKey(), entry.getValue()[0]);
            }
            // 从header取token
            List<String> list = serverHttpRequest.getHeaders().get(TOKEN_HEADER);
            if (CollUtil.isNotEmpty(list)) {
                String accessToken = list.get(0);
                if (StrUtil.startWith(accessToken, TOKEN_HEADER_PREFIX)) {
                    accessToken = accessToken.substring(TOKEN_HEADER_PREFIX.length());
                }
                attributes.put(WebSocketConstant.Attributes.TOKEN, accessToken);
            }
            if (!attributes.containsKey(WebSocketConstant.Attributes.SECRET_KEY) && !attributes.containsKey(WebSocketConstant.Attributes.TOKEN)) {
                // 未携带密钥和token，连接断开
                return false;
            }
            // 密钥链接
            if (attributes.containsKey(WebSocketConstant.Attributes.SECRET_KEY)) {
                // 验证密钥
                String key = SpringUtil.getBean(WebSocketProperties.class).getSecretKey();
                if (!Objects.equals(attributes.get(WebSocketConstant.Attributes.SECRET_KEY), key)) {
                    return false;
                }
            }
            // 获取所有拦截器
            Map<String, SocketInterceptor> interceptorMap = getInterceptors();
            String processor = String.valueOf(attributes.get(WebSocketConstant.Attributes.PROCESSOR));
            if (StrUtil.isNotBlank(processor) && interceptorMap.containsKey(processor)) {
                SocketInterceptor interceptor = interceptorMap.get(processor);
                // 执行type对应的拦截器
                result = interceptor.beforeHandshake(request, response, wsHandler, attributes);
            }
            // group拦截器通过，执行默认拦截器
            if (result) {
                return interceptorMap.get(WebSocketConstant.DEFAULT_PROCESSOR).beforeHandshake(request, response, wsHandler, attributes);
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("握手完成");
        if (request instanceof ServletServerHttpRequest serverHttpRequest) {
            // 获取所有拦截器
            Map<String, SocketInterceptor> interceptorMap = getInterceptors();
            String processor = serverHttpRequest.getServletRequest().getParameter(WebSocketConstant.Attributes.PROCESSOR);
            if (StrUtil.isNotBlank(processor) && interceptorMap.containsKey(processor)) {
                SocketInterceptor interceptor = interceptorMap.get(processor);
                // 执行group对应的拦截器
                interceptor.afterHandshake(request, response, wsHandler, exception);
            }
            interceptorMap.get(WebSocketConstant.DEFAULT_PROCESSOR).afterHandshake(request, response, wsHandler, exception);
        }
    }
}
