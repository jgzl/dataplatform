package cn.cleanarch.dp.system.dataobject.errorcode;

import cn.cleanarch.dp.common.model.BaseDO;
import cn.cleanarch.dp.system.constants.SysErrorCodeTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
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
