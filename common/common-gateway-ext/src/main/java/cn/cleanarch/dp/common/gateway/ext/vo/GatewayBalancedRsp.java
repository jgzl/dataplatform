package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayBalancedDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayLoadServerDO;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author jianglong
 * @Date 2020/06/30
 * @Version V1.0
 */
@Data
public class GatewayBalancedRsp implements java.io.Serializable {
    private GatewayBalancedDO balancedDO;
    private List<GatewayLoadServerDO> serverList;
}
