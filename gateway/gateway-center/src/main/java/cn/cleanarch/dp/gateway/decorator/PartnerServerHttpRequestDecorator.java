package cn.cleanarch.dp.gateway.decorator;

import cn.cleanarch.dp.gateway.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.util.ContentTypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
@Slf4j
public class PartnerServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    PartnerServerHttpRequestDecorator(ServerWebExchange exchange,ServerHttpRequest request, GatewayLogDO gatewayLog) {
        super(request);
        final MediaType contentType = request.getHeaders().getContentType();
        if (contentType == null || ContentTypeUtils.validText(contentType)) {
            Object bodyObject = exchange.getAttribute("cachedRequestBodyObject");
            if (bodyObject !=null) {
                String body = String.valueOf(bodyObject);
                gatewayLog.setRequestBody(body);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("网关只记录xml,json格式的请求相应内容,当前请求的Content-Type为{}", contentType);
            }
        }
    }

}
