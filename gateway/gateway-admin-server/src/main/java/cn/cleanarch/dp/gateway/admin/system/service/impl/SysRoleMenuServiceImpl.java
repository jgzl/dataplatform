package cn.cleanarch.dp.gateway.admin.system.service.impl;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.gateway.admin.system.mapper.SysRoleMenuMapper;
import cn.cleanarch.dp.gateway.admin.system.service.SysRoleMenuService;
import cn.cleanarch.dp.system.domain.SysRoleMenuDO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuDO> implements SysRoleMenuService {

    private final CacheManager cacheManager;

    /**
     * @param role    角色code
     * @param roleId  角色
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, key = "#roleId")
    public Boolean saveRoleMenus(String role, String roleId, String menuIds) {
        this.remove(Wrappers.<SysRoleMenuDO>query().lambda().eq(SysRoleMenuDO::getRoleId, roleId));

        if (StrUtil.isBlank(menuIds)) {
            return Boolean.TRUE;
        }
        List<SysRoleMenuDO> roleMenuList = Arrays.stream(menuIds.split(",")).map(menuId -> {
            SysRoleMenuDO roleMenu = new SysRoleMenuDO();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).collect(Collectors.toList());

        // 清空userinfo
        cacheManager.getCache(CacheConstants.USER_DETAILS).clear();

        roleMenuList.forEach(baseMapper::insert);
        return Boolean.TRUE;
    }

}
