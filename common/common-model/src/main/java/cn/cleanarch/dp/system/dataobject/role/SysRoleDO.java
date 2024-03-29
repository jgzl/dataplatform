package cn.cleanarch.dp.system.dataobject.role;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 角色
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@TableName("sys_role")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysRoleDO extends BaseDO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色标识
     */
    @NotBlank(message = "角色标识不能为空")
    private String roleCode;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 数据权限类型
     */
//    @NotNull(message = "数据权限类型不能为空")
    private Integer dsType;

    /**
     * 数据权限作用范围
     */
    private String dsScope;

}