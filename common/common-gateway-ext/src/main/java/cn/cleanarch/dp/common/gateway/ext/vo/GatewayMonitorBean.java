package cn.cleanarch.dp.common.gateway.ext.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 监控开关
 * @Author JL
 * @Date 2021/04/14
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GatewayMonitorBean implements java.io.Serializable {
    private Boolean checked;
}
