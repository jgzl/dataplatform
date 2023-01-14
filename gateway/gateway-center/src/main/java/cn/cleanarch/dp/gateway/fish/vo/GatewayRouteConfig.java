package cn.cleanarch.dp.gateway.fish.vo;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;

/**
 * @Description 网关路由配置属性
 * @Author JL
 * @Date 2020/05/25
 * @Version V1.0
 */
@Data
public class GatewayRouteConfig implements java.io.Serializable {
    private String id;
    private int order;
    private String uri;
    private String path;
    private String host;
    private String remoteAddr;
    private String method;
    private int stripPrefix;
    private KeyResolver keyResolver;
    private int replenishRate;
    private int burstCapacity;
    private String requestParameterName;
    private String requestParameterValue;
    private String rewritePathName;
    private String rewritePathValue;
    private String hystrixName;
    private String fallbackUri;
    private GatewayFilter [] gatewayFilter;
    private boolean authorize = false;
}
