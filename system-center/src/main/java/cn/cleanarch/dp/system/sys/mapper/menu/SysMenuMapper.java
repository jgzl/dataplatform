package cn.cleanarch.dp.system.sys.mapper.menu;

import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.system.sys.dataobject.menu.SysMenuDO;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
public interface SysMenuMapper extends ExtendBaseMapper<SysMenuDO> {

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return
     */
    List<SysMenuDO> listMenusByRoleId(String roleId);

    /**
     * 通过角色ID查询权限
     *
     * @param roleIds Ids
     * @return
     */
    List<String> listPermissionsByRoleIds(String roleIds);

}
