package cn.cleanarch.dp.gateway.filter.webflux;

import cn.cleanarch.dp.common.core.constant.GatewayConstants;
import cn.cleanarch.dp.common.core.constant.enums.StatusEnum;
import cn.cleanarch.dp.common.core.exception.enums.ErrorCodeConstants;
import cn.cleanarch.dp.common.core.utils.WebfluxUtil;
import cn.cleanarch.dp.common.gateway.support.GatewayAccessCacheHolder;
import cn.cleanarch.dp.gateway.configuration.properties.GatewayProperties;
import cn.cleanarch.dp.gateway.vo.GatewayAccessVO;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 网关访问过滤器
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/16
 */
@Slf4j
public class GatewayApiAccessFilter extends AbstractGatewayApiFilter {

    public static final String FILTER_NAME = "gatewayApiAccessFilter";

    private final GatewayProperties gatewayProperties;

    public GatewayApiAccessFilter(GatewayProperties gatewayProperties) {
        super(gatewayProperties);
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public Mono<Void> match(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String apiKey = WebfluxUtil.getParameterByHeaderOrPath(request, GatewayConstants.X_BUSINESS_API_KEY);
        String apiSecret = WebfluxUtil.getParameterByHeaderOrPath(request, GatewayConstants.X_BUSINESS_API_SECRET);
        String system = WebfluxUtil.getParameterByHeaderOrPath(request, GatewayConstants.X_BUSINESS_API_SYSTEM);
        String type = WebfluxUtil.getParameterByHeaderOrPath(request, GatewayConstants.X_BUSINESS_API_TYPE);
        ServerHttpResponse response = exchange.getResponse();
        if (StrUtil.isBlank(type)) {
            if (StrUtil.isNotBlank(apiKey)&&StrUtil.isNotBlank(apiSecret)) {
                GatewayAccessVO vo = GatewayAccessCacheHolder.get(apiKey);
                if (vo == null) {
                    return WebfluxUtil.errorOut(response, ErrorCodeConstants.GATEWAY_ACCESS_API_KEY_NOT_VALID);
                }
                if (!vo.getApiSecret().equals(apiSecret)) {
                    return WebfluxUtil.errorOut(response, ErrorCodeConstants.GATEWAY_ACCESS_API_SECRET_NOT_VALID);
                }
                if (!vo.getSystem().equals(system)) {
                    return WebfluxUtil.errorOut(response, ErrorCodeConstants.GATEWAY_ACCESS_API_SYSTEM_NOT_VALID);
                }
                if (StatusEnum.DISABLE.getCode().equals(vo.getStatus())) {
                    return WebfluxUtil.errorOut(response, ErrorCodeConstants.GATEWAY_ACCESS_DISABLED);
                }
            } else {
                return WebfluxUtil.errorOut(response, ErrorCodeConstants.GATEWAY_ACCESS_INFO_NOT_NULL);
            }
        } else if ("none".equals(type)) {
            return chain.filter(exchange);
        } else if ("token".equals(type)) {
            return WebfluxUtil.errorOut(response, ErrorCodeConstants.GATEWAY_ACCESS_AUTHENTICATION_NOT_EXIST);
        } else {
            return WebfluxUtil.errorOut(response, ErrorCodeConstants.GATEWAY_ACCESS_AUTHENTICATION_NOT_EXIST);
        }
        return chain.filter(exchange);
    }
}
