package cn.cleanarch.dp.gateway.filter;

import cn.cleanarch.dp.common.gateway.ext.util.HttpResponseUtils;
import cn.cleanarch.dp.common.gateway.ext.util.NetworkIpUtils;
import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
import cn.cleanarch.dp.gateway.cache.IpListCache;
import cn.cleanarch.dp.gateway.cache.RegServerCache;
import cn.cleanarch.dp.gateway.vo.GatewayRegServer;
import lombok.extern.slf4j.Slf4j;
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
 * @Description IP过滤
 * @Author JL
 * @Date 2020/05/19
 * @Version V1.0
 */
@Slf4j
public class IpGatewayFilter implements GatewayFilter, Ordered {

    private final String routeId;
    public IpGatewayFilter(String routeId){
        this.routeId = routeId;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //做了负载均衡的route服务不做客户端IP验证
        if (routeId.startsWith(GatewayRouteConstants.BALANCED)){
            return chain.filter(exchange);
        }
        String ip = NetworkIpUtils.getIpAddress(request);
        if (!this.isPassIp(ip)){
            String msg = "客户端IP已被限制，无权限访问网关!" +" ip:" + ip;
            log.error(msg);
            return HttpResponseUtils.writeUnauth(exchange.getResponse(), msg);
        }
        GatewayRegServer regServer = getCacheRegServer(ip);
        if (regServer == null){
            String msg = "客户端IP未注册使用，无权限访问网关路由："+ routeId +"! Ip:" + ip;
            log.error(msg);
            return HttpResponseUtils.writeUnauth(exchange.getResponse(), msg);
        }
        return chain.filter(exchange);
    }

    /**
     * 查询和对比缓存中的注册客户端
     * @param ip
     * @return
     */
    public GatewayRegServer getCacheRegServer(String ip){
        List<GatewayRegServer> regServers = RegServerCache.get(routeId);
        if (CollectionUtils.isEmpty(regServers)){
            return null;
        }
        Optional<GatewayRegServer> optional = regServers.stream().filter(r -> ip.equals(r.getIp())).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 是否允许通行IP
     * @return
     */
    private boolean isPassIp(String ip){
        Object object = IpListCache.get(ip);
        //如果没有设置IP白名单，则不做限制
        if (object == null){
            return true;
        }
        return (boolean) object;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
