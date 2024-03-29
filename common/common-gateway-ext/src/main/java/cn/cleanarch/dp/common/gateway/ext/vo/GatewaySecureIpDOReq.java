package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewaySecureIpDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 对前端请求进行接收与封装bean
 * @Author jianglong
 * @Date 2020/05/28
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GatewaySecureIpDOReq extends GatewaySecureIpDO implements java.io.Serializable {
    private Integer currentPage;
    private Integer pageSize;
}
