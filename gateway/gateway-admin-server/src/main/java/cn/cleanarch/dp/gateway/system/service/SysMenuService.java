package cn.cleanarch.dp.gateway.system.service;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.system.domain.SysMenuDO;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
public interface SysMenuService extends IService<SysMenuDO> {

    /**
     * 通过角色编号查询URL 权限
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenuDO> findMenuByRoleId(String roleId);

    /**
     * 级联删除菜单
     *
     * @param id 菜单ID
     * @return 成功、失败
     */
    R removeMenuById(String id);

    /**
     * 更新菜单信息
     *
     * @param sysMenuDO 菜单信息
     * @return 成功、失败
     */
    Boolean updateMenuById(SysMenuDO sysMenuDO);

    /**
     * 构建树
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return
     */
    List<Tree<String>> treeMenu(boolean lazy, String parentId);

    /**
     * 查询菜单
     *
     * @param voSet
     * @param parentId
     * @return
     */
    List<Tree<String>> filterMenu(Set<SysMenuDO> voSet, String type, String parentId);

}
