package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayMonitorDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
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
public class GatewayRouteDORsp extends GatewayRouteDO {
    private GatewayMonitorDO monitor;
}
