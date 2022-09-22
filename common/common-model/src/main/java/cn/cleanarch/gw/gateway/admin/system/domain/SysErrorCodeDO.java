package cn.cleanarch.gw.gateway.admin.system.domain;

import cn.cleanarch.gw.common.BaseDO;
import cn.cleanarch.gw.gateway.admin.system.constants.SysErrorCodeTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 错误码表
 *
 * @author lihaifeng
 */
@TableName(value = "sys_error_code")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysErrorCodeDO extends BaseDO {

    private static final long serialVersionUID = 1L;
    /**
     * 错误码类型
     *
     * 枚举 {@link SysErrorCodeTypeEnum}
     */
    private Integer type;
    /**
     * 应用名
     */
    private String applicationName;
    /**
     * 错误码编码
     */
    private Integer code;
    /**
     * 错误码错误提示
     */
    private String message;
    /**
     * 错误码备注
     */
    private String memo;

}