package cn.cleanarch.dp.gateway.decorator;

import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
public class PayloadServerWebExchangeDecorator extends ServerWebExchangeDecorator {

    private final PartnerServerHttpRequestDecorator requestDecorator;

    private final PartnerServerHttpResponseDecorator responseDecorator;

    public PayloadServerWebExchangeDecorator(ServerWebExchange delegate, GatewayLogDO gatewayLog) {
        super(delegate);
        requestDecorator = new PartnerServerHttpRequestDecorator(delegate, delegate.getRequest(), gatewayLog);
        responseDecorator = new PartnerServerHttpResponseDecorator(delegate, delegate.getResponse(), gatewayLog);
    }

    @Override
    public ServerHttpRequest getRequest() {
        return requestDecorator;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return responseDecorator;
    }
}
