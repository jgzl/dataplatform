package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author JL
 * @Date 2020/12/30
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GatewayRouteDOCountRsp extends GatewayRouteDO implements java.io.Serializable {
    /**
     * 统计量
     */
    private Integer count;

}
