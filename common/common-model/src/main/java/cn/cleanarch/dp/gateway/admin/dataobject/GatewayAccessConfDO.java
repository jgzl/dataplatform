package cn.cleanarch.dp.gateway.admin.dataobject;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("gateway_access_conf")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GatewayAccessConfDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 网关访问key
     */
    private String apiKey;

    /**
     * 网关访问secret
     */
    private String apiSecret;

    /**
     * 访问系统
     */
    @TableField(value = "`system`")
    private String system;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private String status;

}