package cn.cleanarch.gw.common.model.gateway.domain;

import cn.cleanarch.gw.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 路由
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GatewayRouteConf extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 路由ID
     */
    private String routeId;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 断言
     */
    private String predicates;

    /**
     * 过滤器
     */
    private String filters;

    /**
     * uri
     */
    private String uri;

    /**
     * 排序
     */
    private Integer sort;

    private String metadata;

}
