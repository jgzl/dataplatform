package cn.cleanarch.dp.gateway.filter;

import cn.cleanarch.dp.common.gateway.ext.util.HttpResponseUtils;
import cn.cleanarch.dp.common.gateway.ext.util.NetworkIpUtils;
import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
import cn.cleanarch.dp.gateway.cache.RegServerCache;
import cn.cleanarch.dp.gateway.vo.GatewayRegServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * @Description 客户端ID过滤
 * @Author JL
 * @Date 2020/05/19
 * @Version V1.0
 */
@Slf4j
public class ClientIdGatewayFilter implements GatewayFilter, Ordered {

    private final String routeId;

    public ClientIdGatewayFilter(String routeId){
        this.routeId = routeId;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //做了负载均衡的route服务不做客户端ID验证
        if (routeId.startsWith(GatewayRouteConstants.BALANCED)){
            return chain.filter(exchange);
        }
        String ip = NetworkIpUtils.getIpAddress(request);
        String clientId = this.getClientId(request);
        if (StringUtils.isBlank(clientId)){
            String msg = "客户端ID值为空，无权限访问网关路由："+ routeId +"! Ip:" + ip;
            log.error(msg);
            return HttpResponseUtils.writeUnauth(exchange.getResponse(), msg);
        }
        GatewayRegServer regServer = getCacheRegServer(clientId);
        if (regServer == null){
            String msg = "客户端ID未注册使用，无权限访问网关路由："+ routeId +"! Ip:" + ip;
            log.error(msg);
            return HttpResponseUtils.writeUnauth(exchange.getResponse(), msg);
        }
        return chain.filter(exchange);
    }

    /**
     * 查询和对比缓存中的注册客户端
     * @param clientId
     * @return
     */
    public GatewayRegServer getCacheRegServer(String clientId){
        List<GatewayRegServer> regServers = RegServerCache.get(routeId);
        if (CollectionUtils.isEmpty(regServers)){
            return null;
        }
        Optional<GatewayRegServer> optional = regServers.stream().filter(r -> clientId.equals(r.getClientId())).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 获取请求头部的clientId值
     * @param request
     * @return
     */
    public String getClientId(ServerHttpRequest request){
        String clientId = request.getQueryParams().getFirst(GatewayRouteConstants.CLIENT_ID);
        if (StringUtils.isBlank(clientId)){
            clientId = request.getHeaders().getFirst(GatewayRouteConstants.CLIENT_ID);
        }
        return clientId;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
