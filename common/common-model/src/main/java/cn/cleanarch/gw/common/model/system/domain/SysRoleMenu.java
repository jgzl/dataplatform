package cn.cleanarch.gw.common.model.system.domain;

import cn.cleanarch.gw.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色菜单
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenu extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;

}
