package cn.cleanarch.dp.gateway.admin.system.service;

import cn.cleanarch.dp.system.domain.SysRoleMenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
public interface SysRoleMenuService extends IService<SysRoleMenuDO> {

    /**
     * 更新角色菜单
     *
     * @param role
     * @param roleId  角色
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return
     */
    Boolean saveRoleMenus(String role, String roleId, String menuIds);

}
