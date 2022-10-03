package cn.cleanarch.dp.gateway.admin.system.service.impl;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.gateway.admin.system.mapper.SysRoleMapper;
import cn.cleanarch.dp.gateway.admin.system.service.SysRoleMenuService;
import cn.cleanarch.dp.gateway.admin.system.service.SysRoleService;
import cn.cleanarch.dp.system.domain.SysRoleDO;
import cn.cleanarch.dp.system.domain.SysRoleMenuDO;
import cn.cleanarch.dp.system.vo.SysRoleMenuVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {

    private SysRoleMenuService roleMenuService;

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public List findRolesByUserId(String userId) {
        return baseMapper.listRolesByUserId(userId);
    }

    /**
     * 根据角色ID 查询角色列表，注意缓存删除
     *
     * @param roleIdList 角色ID列表
     * @param key        缓存key
     * @return
     */
    @Override
    @Cacheable(value = CacheConstants.ROLE_DETAILS, key = "#key")
    public List<SysRoleDO> findRolesByRoleIds(List<String> roleIdList, String key) {
        return baseMapper.selectBatchIds(roleIdList);
    }

    /**
     * 通过角色ID，删除角色,并清空角色菜单缓存
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeRoleById(String id) {
        roleMenuService.remove(Wrappers.<SysRoleMenuDO>update().lambda().eq(SysRoleMenuDO::getRoleId, id));
        return this.removeById(id);
    }

    /**
     * 根据角色菜单列表
     *
     * @param sysRoleMenuVO 角色&菜单列表
     * @return
     */
    @Override
    public Boolean updateRoleMenus(SysRoleMenuVO sysRoleMenuVO) {
        SysRoleDO sysRoleDO = baseMapper.selectById(sysRoleMenuVO.getRoleId());
        return roleMenuService.saveRoleMenus(sysRoleDO.getRoleCode(), sysRoleMenuVO.getRoleId(), sysRoleMenuVO.getMenuIds());
    }

    /**
     * 根据角色code查找角色
     *
     * @param role
     * @return
     */
    @Override
    public SysRoleDO findRoleByRoleCode(String role) {
        SysRoleDO sysRoleDO = baseMapper.selectOne(Wrappers.<SysRoleDO>query().lambda().eq(SysRoleDO::getRoleCode, role));
        return sysRoleDO;
    }

}
