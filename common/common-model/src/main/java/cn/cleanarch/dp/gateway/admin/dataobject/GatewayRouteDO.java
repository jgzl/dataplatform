package cn.cleanarch.dp.gateway.admin.dataobject;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 路由
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@TableName("gateway_route_conf")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GatewayRouteDO extends BaseDO {

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
