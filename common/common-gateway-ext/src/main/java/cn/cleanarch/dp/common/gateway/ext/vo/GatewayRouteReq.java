package cn.cleanarch.dp.common.gateway.ext.vo;

import lombok.Data;

/**
 * @Description
 * @Author jianglong
 * @Date 2020/05/11
 * @Version V1.0
 */
@Data
public class GatewayRouteReq implements java.io.Serializable {

    //表单值
    private GatewayRouteDOFormBean form;
    //过滤器开关
    private GatewayRouteFilterBean filter;
    //熔断器开关
    private GatewayRouteHystrixBean hystrix;
    //限流器开关
    private GatewayRouteLimiterBean limiter;
    //鉴权器开关
    private GatewayRouteAccessBean access;
    //监控开关
    private GatewayMonitorBean monitor;

    private Integer currentPage;
    private Integer pageSize;
}
