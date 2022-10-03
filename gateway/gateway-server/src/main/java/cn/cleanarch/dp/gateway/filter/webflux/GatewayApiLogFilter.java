package cn.cleanarch.dp.gateway.filter.webflux;

import cn.cleanarch.dp.common.core.constant.GatewayConstants;
import cn.cleanarch.dp.common.core.utils.JacksonUtil;
import cn.cleanarch.dp.common.core.utils.WebfluxUtil;
import cn.cleanarch.dp.gateway.configuration.properties.GatewayProperties;
import cn.cleanarch.dp.gateway.decorator.PayloadServerWebExchangeDecorator;
import cn.cleanarch.dp.gateway.domain.GatewayLogDO;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 日志环绕过滤器
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/16
 */
@Slf4j
public class GatewayApiLogFilter extends AbstractGatewayApiFilter {

    public static final String FILTER_NAME = "gatewayApiLogFilter";

    private static final String GATEWAY_START_LOG_TIME = GatewayApiLogFilter.class.getName() + ".GATEWAY_START_LOG_TIME";

    private final GatewayProperties gatewayProperties;

    public GatewayApiLogFilter(GatewayProperties gatewayProperties) {
        super(gatewayProperties);
        this.gatewayProperties = gatewayProperties;
    }
    @Override
    public Mono<Void> match(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String rawPath = uri.getRawPath();
        String query = uri.getQuery();
        String pathAndQuery = StrUtil.isBlank(query) ? rawPath : rawPath + "?" + query;
        HttpHeaders httpHeaders = request.getHeaders();
        String fastUUID = IdUtil.fastUUID();
        String methodValue = Optional.ofNullable(request.getMethod()).orElse(HttpMethod.GET).name();
        String jsonHeader = JacksonUtil.toJsonString(httpHeaders);

        if (log.isDebugEnabled()) {
            log.debug("访问[{}]header的json格式为:[{}]", rawPath, jsonHeader);
        }

        String env = WebfluxUtil.getParameterByHeaderOrPath(request, GatewayConstants.X_BUSINESS_API_ENV);
        String apiKey = WebfluxUtil.getParameterByHeaderOrPath(request, GatewayConstants.X_BUSINESS_API_KEY);
        String apiSecret = WebfluxUtil.getParameterByHeaderOrPath(request, GatewayConstants.X_BUSINESS_API_SECRET);
        String system = WebfluxUtil.getParameterByHeaderOrPath(request, GatewayConstants.X_BUSINESS_API_SYSTEM);

        GatewayLogDO gatewayLog = new GatewayLogDO();
        gatewayLog.setId(fastUUID);
        gatewayLog.setRequestHeader(jsonHeader);
        gatewayLog.setRequestPath(rawPath);
        gatewayLog.setRequestPathAndQuery(pathAndQuery);
        gatewayLog.setRequestMethod(methodValue);
        gatewayLog.setRequestTime(LocalDateTime.now());
        gatewayLog.setEnvironment(env);
        gatewayLog.setApiKey(apiKey);
        gatewayLog.setApiSecret(apiSecret);
        gatewayLog.setSourceService(system);
        return chain.filter(new PayloadServerWebExchangeDecorator(exchange, gatewayLog));
    }

    @Override
    public Mono<Void> disMatch(ServerWebExchange exchange, WebFilterChain chain) {
        /**
         * 非网关统一前缀访问路径访问不记录日志信息
         */
        exchange.getAttributes().put(GATEWAY_START_LOG_TIME, Instant.now().toEpochMilli());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    long startTime = exchange.getAttribute(GATEWAY_START_LOG_TIME);
                    long endTime = (Instant.now().toEpochMilli() - startTime);
                    log.info("结束访问[{}],共消耗时间为:{}ms", exchange.getRequest().getURI().getRawPath(), endTime);
                })
        );
    }
}
