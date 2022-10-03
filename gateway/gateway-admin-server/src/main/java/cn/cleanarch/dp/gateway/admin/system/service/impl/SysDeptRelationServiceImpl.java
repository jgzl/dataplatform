package cn.cleanarch.dp.gateway.admin.system.service.impl;

import cn.cleanarch.dp.gateway.admin.system.mapper.SysDeptRelationMapper;
import cn.cleanarch.dp.gateway.admin.system.service.SysDeptRelationService;
import cn.cleanarch.dp.system.domain.SysDeptDO;
import cn.cleanarch.dp.system.domain.SysDeptRelationDO;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2018-02-12
 */
@Service
@AllArgsConstructor
public class SysDeptRelationServiceImpl extends ServiceImpl<SysDeptRelationMapper, SysDeptRelationDO>
        implements SysDeptRelationService {

    private final SysDeptRelationMapper sysDeptRelationMapper;

    /**
     * 维护部门关系
     *
     * @param sysDeptDO 部门
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDeptRelation(SysDeptDO sysDeptDO) {
        // 增加部门关系表
        List<SysDeptRelationDO> relationList = sysDeptRelationMapper.selectList(
                        Wrappers.<SysDeptRelationDO>query().lambda().eq(SysDeptRelationDO::getDescendant, sysDeptDO.getParentId()))
                .stream().map(relation -> {
                    relation.setDescendant(sysDeptDO.getId());
                    return relation;
                }).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(relationList)) {
            relationList.forEach(baseMapper::insert);
        }

        // 自己也要维护到关系表中
        SysDeptRelationDO own = new SysDeptRelationDO();
        own.setDescendant(sysDeptDO.getId());
        own.setAncestor(sysDeptDO.getId());
        sysDeptRelationMapper.insert(own);
    }

    /**
     * 通过ID删除部门关系
     *
     * @param id
     */
    @Override
    public void deleteAllDeptRelation(String id) {
        baseMapper.deleteDeptRelationsById(id);
    }

    /**
     * 更新部门关系
     *
     * @param relation
     */
    @Override
    public void updateDeptRelation(SysDeptRelationDO relation) {
        baseMapper.updateDeptRelations(relation);
    }

}
