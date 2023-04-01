package cn.cleanarch.dp.gateway.filter.authorize;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @Description 责任链设计模式，抽象业务父类
 * @Author jianglong
 * @Date 2020/05/25
 * @Version V1.0
 */
public abstract class FilterHandler {

    public FilterHandler handler = null;
    protected GatewayRouteDO gatewayRouteDO;

    public void handler(ServerHttpRequest request, GatewayRouteDO gatewayRouteDO){
        this.gatewayRouteDO = gatewayRouteDO;
        handleRequest(request);
        nextHandle(request);
    }

    public abstract void handleRequest(ServerHttpRequest request);

    public void nextHandle(ServerHttpRequest request){
        if (handler != null){
            handler.handler(request, gatewayRouteDO);
        }
    }
}
