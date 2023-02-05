package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayLoadServerDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author jianglong
 * @Date 2020/06/28
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GatewayLoadServerDOReq extends GatewayLoadServerDO implements java.io.Serializable {
    private Integer currentPage;
    private Integer pageSize;
}
