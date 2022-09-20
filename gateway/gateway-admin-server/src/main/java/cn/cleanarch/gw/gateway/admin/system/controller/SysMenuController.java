package cn.cleanarch.gw.gateway.admin.system.controller;

import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.model.system.domain.SysMenu;
import cn.cleanarch.gw.common.security.utils.AppContextHolder;
import cn.cleanarch.gw.gateway.admin.system.service.SysMenuService;
import cn.cleanarch.gw.gateway.admin.system.service.SysRoleService;
import cn.hutool.core.lang.tree.Tree;
import lombok.AllArgsConstructor;
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
    public R<List<Tree<Long>>> getTreeWithUser(String type, Long parentId) {
        Set<SysMenu> all = new HashSet<>();
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
    public R<List<Tree<Long>>> getTreeWithRole(String type, Long parentId, @RequestParam Long roleId) {
        // 获取符合条件的菜单
        Set<SysMenu> all = new HashSet<>(sysMenuService.findMenuByRoleId(roleId));
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
    public R<List<Tree<Long>>> getTree(boolean lazy, Long parentId) {
        return R.success(sysMenuService.treeMenu(lazy, parentId));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @GetMapping("/list/{roleId}")
    public R<List<Long>> getRoleTree(@PathVariable Long roleId) {
        return R.success(
                sysMenuService.findMenuByRoleId(roleId).stream().map(SysMenu::getId).collect(Collectors.toList()));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    public R<SysMenu> getById(@PathVariable Long id) {
        return R.success(sysMenuService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return success/false
     */
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('sys_menu_add')")
    public R<SysMenu> save(@Valid @RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return R.success(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    //@PreAuthorize("@pms.hasPermission('sys_menu_del')")
    public R<Boolean> removeById(@PathVariable Long id) {
        return sysMenuService.removeMenuById(id);
    }

    /**
     * 更新菜单
     *
     * @param sysMenu 菜单对象
     * @return
     */
    @PutMapping
    //@PreAuthorize("@pms.hasPermission('sys_menu_edit')")
    public R<Boolean> update(@Valid @RequestBody SysMenu sysMenu) {
        return R.success(sysMenuService.updateMenuById(sysMenu));
    }

}
