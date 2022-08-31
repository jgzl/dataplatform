package cn.cleanarch.gw.gateway.admin.system.service.impl;

import cn.cleanarch.gw.common.core.constant.CacheConstants;
import cn.cleanarch.gw.common.core.constant.CommonConstants;
import cn.cleanarch.gw.common.core.constant.enums.MenuTypeEnum;
import cn.cleanarch.gw.common.core.exception.enums.ErrorCodeConstants;
import cn.cleanarch.gw.common.core.exception.util.ServiceExceptionUtil;
import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.model.system.domain.SysMenu;
import cn.cleanarch.gw.common.model.system.domain.SysRoleMenu;
import cn.cleanarch.gw.gateway.admin.system.mapper.SysMenuMapper;
import cn.cleanarch.gw.gateway.admin.system.mapper.SysRoleMenuMapper;
import cn.cleanarch.gw.gateway.admin.system.service.SysMenuService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId", unless = "#result.isEmpty()")
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public R removeMenuById(Integer id) {
        // 查询父节点为当前节点的节点
        List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, id));
        if (CollUtil.isNotEmpty(menuList)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.MENU_EXISTS_CHILDREN);
        }

        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getMenuId, id));
        // 删除当前菜单及其子菜单
        return R.success(this.removeById(id));
    }

    @Override
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public Boolean updateMenuById(SysMenu sysMenu) {
        return this.updateById(sysMenu);
    }

    /**
     * 构建树查询 1. 不是懒加载情况，查询全部 2. 是懒加载，根据parentId 查询 2.1 父节点为空，则查询ID -1
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return
     */
    @Override
    public List<Tree<Long>> treeMenu(boolean lazy, Long parentId) {
        if (!lazy) {
            List<TreeNode<Long>> collect = baseMapper
                    .selectList(Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSort)).stream()
                    .map(getNodeFunction()).collect(Collectors.toList());

            return TreeUtil.build(collect, CommonConstants.TREE_ROOT_ID);
        }

        Long parent = parentId == null ? CommonConstants.TREE_ROOT_ID : parentId;

        List<TreeNode<Long>> collect = baseMapper
                .selectList(
                        Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parent).orderByAsc(SysMenu::getSort))
                .stream().map(getNodeFunction()).collect(Collectors.toList());

        return TreeUtil.build(collect, parent);
    }

    /**
     * 查询菜单
     *
     * @param all      全部菜单
     * @param type     类型
     * @param parentId 父节点ID
     * @return
     */
    @Override
    public List<Tree<Long>> filterMenu(Set<SysMenu> all, String type, Long parentId) {
        List<TreeNode<Long>> collect;
        if (StrUtil.isBlank(type)) {
            collect = all.stream().filter(menu -> menu.getType().equals(MenuTypeEnum.LEFT_MENU.getType())).map(getNodeFunction())
                    .collect(Collectors.toList());
        } else {
            String[] typeArray = type.split(",");
            collect = all.stream().filter(menu -> ArrayUtil.contains(typeArray, menu.getType())).map(getNodeFunction())
                    .collect(Collectors.toList());
        }
        Long parent = parentId == null ? CommonConstants.TREE_ROOT_ID : parentId;
        return TreeUtil.build(collect, parent);
    }

    @NotNull
    private Function<SysMenu, TreeNode<Long>> getNodeFunction() {
        return menu -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(menu.getMenuId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSort());
            // 扩展属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("menuId", menu.getMenuId());
            extra.put("name", menu.getName());
            extra.put("sort", menu.getSort());
            extra.put("keepAlive", menu.getKeepAlive());
            extra.put("path",menu.getPath());
            extra.put("component",menu.getComponent());
            Map<String,String> meta = new HashMap<>();
            extra.put("meta",meta);
            meta.put("title",menu.getName());
            meta.put("type",menu.getType());
            meta.put("icon",menu.getIcon());
            node.setExtra(extra);
            return node;
        };
    }

}
