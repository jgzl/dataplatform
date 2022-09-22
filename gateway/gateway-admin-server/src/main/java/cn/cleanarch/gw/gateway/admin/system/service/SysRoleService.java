package cn.cleanarch.gw.gateway.admin.system.service;

import cn.cleanarch.gw.gateway.admin.system.domain.SysRoleDO;
import cn.cleanarch.gw.gateway.admin.system.vo.SysRoleMenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
public interface SysRoleService extends IService<SysRoleDO> {

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRoleDO> findRolesByUserId(Long userId);

    /**
     * 根据角色ID 查询角色列表
     *
     * @param roleIdList 角色ID列表
     * @param key        缓存key
     * @return
     */
    List<SysRoleDO> findRolesByRoleIds(List<Long> roleIdList, String key);

    /**
     * 通过角色ID，删除角色
     *
     * @param id
     * @return
     */
    Boolean removeRoleById(Long id);

    /**
     * 根据角色菜单列表
     *
     * @param sysRoleMenuVO 角色&菜单列表
     * @return
     */
    Boolean updateRoleMenus(SysRoleMenuVO sysRoleMenuVO);

    /**
     * 根据角色code查找角色
     *
     * @param role
     * @return
     */
    SysRoleDO findRoleByRoleCode(String role);
}
