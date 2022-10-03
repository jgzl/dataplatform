package cn.cleanarch.dp.gateway.admin.system.service;

import cn.cleanarch.dp.system.domain.SysDeptDO;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2018-01-20
 */
public interface SysDeptService extends IService<SysDeptDO> {

    /**
     * 查询部门树菜单
     *
     * @return 树
     */
    List<Tree<String>> selectTree();

    /**
     * 添加信息部门
     *
     * @param sysDeptDO
     * @return
     */
    Boolean saveDept(SysDeptDO sysDeptDO);

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    Boolean removeDeptById(String id);

    /**
     * 更新部门
     *
     * @param sysDeptDO 部门信息
     * @return 成功、失败
     */
    Boolean updateDeptById(SysDeptDO sysDeptDO);

}
