package cn.cleanarch.gw.gateway.admin.system.service.impl;

import cn.cleanarch.gw.common.model.system.domain.SysMenu;
import cn.cleanarch.gw.gateway.admin.security.utils.SecurityUtils;
import cn.cleanarch.gw.gateway.admin.system.service.PermissionService;
import cn.cleanarch.gw.gateway.admin.system.service.SysMenuService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("pms")
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final SysMenuService menuService;

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(permissions)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        List<String> currentUserRoles = SecurityUtils.getRoles();
        if (CollUtil.isEmpty(currentUserRoles)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
//        if (roleService.hasAnySuperAdmin(roleIds)) {
//            return true;
//        }

        // 遍历权限，判断是否有一个满足
        return Arrays.stream(permissions).anyMatch(permission -> {
            List<SysMenu> menuList = menuService.list(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getPermission,permission));
            // 采用严格模式，如果权限找不到对应的 Menu 的话，认为
            return !CollUtil.isEmpty(menuList);
            // 获得是否拥有该权限，任一一个
        });
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }
        return CollUtil.containsAny(SecurityUtils.getRoles(), Sets.newHashSet(roles));
    }
}
