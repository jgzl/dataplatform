package cn.cleanarch.gw.gateway.admin.system.service.impl;

import cn.cleanarch.gw.common.core.constant.CommonConstants;
import cn.cleanarch.gw.common.model.system.domain.SysDept;
import cn.cleanarch.gw.common.model.system.domain.SysDeptRelation;
import cn.cleanarch.gw.gateway.admin.system.mapper.SysDeptMapper;
import cn.cleanarch.gw.gateway.admin.system.service.SysDeptRelationService;
import cn.cleanarch.gw.gateway.admin.system.service.SysDeptService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2018-01-20
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptRelationService sysDeptRelationService;

    private final SysDeptMapper deptMapper;

    /**
     * 添加信息部门
     *
     * @param dept 部门
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDept(SysDept dept) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dept, sysDept);
        this.save(sysDept);
        sysDeptRelationService.insertDeptRelation(sysDept);
        return Boolean.TRUE;
    }

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeDeptById(Long id) {
        // 级联删除部门
        List<Long> idList = sysDeptRelationService
                .list(Wrappers.<SysDeptRelation>query().lambda().eq(SysDeptRelation::getAncestor, id)).stream()
                .map(SysDeptRelation::getDescendant).collect(Collectors.toList());

        if (CollUtil.isNotEmpty(idList)) {
            this.removeByIds(idList);
        }

        // 删除部门级联关系
        sysDeptRelationService.deleteAllDeptRelation(id);
        return Boolean.TRUE;
    }

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDeptById(SysDept sysDept) {
        // 更新部门状态
        this.updateById(sysDept);
        // 更新部门关系
        SysDeptRelation relation = new SysDeptRelation();
        relation.setAncestor(sysDept.getParentId());
        relation.setDescendant(sysDept.getId());
        sysDeptRelationService.updateDeptRelation(relation);
        return Boolean.TRUE;
    }

    /**
     * 查询全部部门树
     *
     * @return 树
     */
    @Override
    public List<Tree<Long>> selectTree() {
        // 查询全部部门
        List<SysDept> deptAllList = deptMapper.selectList(Wrappers.emptyWrapper());

        // 权限内部门
        List<TreeNode<Long>> collect = deptAllList.stream()
                .filter(dept -> dept.getId().intValue() != dept.getParentId())
                .sorted(Comparator.comparingInt(SysDept::getSort)).map(dept -> {
                    TreeNode<Long> treeNode = new TreeNode<>();
                    treeNode.setId(dept.getId());
                    treeNode.setParentId(dept.getParentId());
                    treeNode.setName(dept.getName());
                    Map<String, Object> extraMap = MapUtil.newHashMap();
                    treeNode.setExtra(extraMap);
                    extraMap.put("deptId", dept.getId());
                    extraMap.put("sort", dept.getSort());
                    extraMap.put("status", dept.getStatus());
                    extraMap.put("label", dept.getName());
                    extraMap.put("value", dept.getId());
                    extraMap.put("remark", null);
                    extraMap.put("createTime", dept.getCreateTime());
                    return treeNode;
                }).collect(Collectors.toList());

        return TreeUtil.build(collect, CommonConstants.TREE_ROOT_ID);
    }

}
