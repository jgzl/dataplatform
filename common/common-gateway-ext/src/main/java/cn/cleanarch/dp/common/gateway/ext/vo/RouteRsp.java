package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.Monitor;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Route;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author JL
 * @Date 2021/04/16
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RouteRsp extends Route {
    private Monitor monitor;
}
