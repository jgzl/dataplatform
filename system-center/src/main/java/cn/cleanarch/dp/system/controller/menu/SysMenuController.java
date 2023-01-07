package cn.cleanarch.dp.system.controller.menu;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.oauth.util.AppContextHolder;
import cn.cleanarch.dp.system.service.menu.SysMenuService;
import cn.cleanarch.dp.system.service.role.SysRoleService;
import cn.cleanarch.dp.system.dataobject.menu.SysMenuDO;
import cn.hutool.core.lang.tree.Tree;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单管理模块
 *
 * @author li7hai26@gmail.com
 * @date 2017/10/31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/system/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    private final SysRoleService sysRoleService;

    /**
     * 返回当前用户的树形菜单集合
     *
     * @param type     类型
     * @param parentId 父节点ID
     * @return 当前用户的树形菜单
     */
    @GetMapping(value = "/tree/user")
    @PreAuthorize("@pms.hasPermission('system:sys-menu:query')")
    public R<List<Tree<String>>> getTreeWithUser(String type, String parentId) {
        Set<SysMenuDO> all = new HashSet<>();
        // 获取符合条件的菜单
        AppContextHolder.getRoles().stream()
                .map(sysRoleService::findRoleByRoleCode)
                .forEach(sysRole -> all.addAll(sysMenuService.findMenuByRoleId(sysRole.getId())));
        return R.success(sysMenuService.filterMenu(all, type, parentId));
    }

    /**
     * 返回特定角色的树形菜单集合
     *
     * @param type     类型
     * @param parentId 父节点ID
     * @param roleId   角色id
     * @return 当前用户的树形菜单
     */
    @GetMapping(value = "/tree/role")
    @PreAuthorize("@pms.hasPermission('system:sys-menu:query')")
    public R<List<Tree<String>>> getTreeWithRole(String type, String parentId, @RequestParam String roleId) {
        // 获取符合条件的菜单
        Set<SysMenuDO> all = new HashSet<>(sysMenuService.findMenuByRoleId(roleId));
        return R.success(sysMenuService.filterMenu(all, type, parentId));
    }

    /**
     * 返回树形菜单集合
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    @PreAuthorize("@pms.hasPermission('system:sys-menu:query')")
    public R<List<Tree<String>>> getTree(boolean lazy, String parentId) {
        return R.success(sysMenuService.treeMenu(lazy, parentId));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @GetMapping("/list/{roleId}")
    @PreAuthorize("@pms.hasPermission('system:sys-menu:query')")
    public R<List<String>> getRoleTree(@PathVariable String roleId) {
        return R.success(
                sysMenuService.findMenuByRoleId(roleId).stream().map(SysMenuDO::getId).collect(Collectors.toList()));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('system:sys-menu:query')")
    public R<SysMenuDO> getById(@PathVariable String id) {
        return R.success(sysMenuService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param sysMenuDO 菜单信息
     * @return success/false
     */
    @PostMapping
    @PreAuthorize("@pms.hasPermission('system:sys-menu:create')")
    public R<SysMenuDO> save(@Valid @RequestBody SysMenuDO sysMenuDO) {
        sysMenuService.save(sysMenuDO);
        return R.success(sysMenuDO);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('system:sys-menu:delete')")
    public R<Boolean> removeById(@PathVariable String id) {
        return sysMenuService.removeMenuById(id);
    }

    /**
     * 更新菜单
     *
     * @param sysMenuDO 菜单对象
     * @return
     */
    @PutMapping
    @PreAuthorize("@pms.hasPermission('system:sys-menu:update')")
    public R<Boolean> update(@Valid @RequestBody SysMenuDO sysMenuDO) {
        return R.success(sysMenuService.updateMenuById(sysMenuDO));
    }

}
