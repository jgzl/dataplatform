package cn.cleanarch.dp.system.dataobject.role;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 角色菜单
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@TableName("sys_role_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysRoleMenuDO extends BaseDO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

}
