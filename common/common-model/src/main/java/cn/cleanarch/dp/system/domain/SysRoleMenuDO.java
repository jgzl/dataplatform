package cn.cleanarch.dp.system.domain;

import cn.cleanarch.dp.common.model.BaseDO;
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

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

}
