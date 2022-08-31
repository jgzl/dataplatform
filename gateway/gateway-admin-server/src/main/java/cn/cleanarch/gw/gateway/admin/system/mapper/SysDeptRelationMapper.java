package cn.cleanarch.gw.gateway.admin.system.mapper;

import cn.cleanarch.gw.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.gw.common.model.system.domain.SysDeptRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2018-02-12
 */
@Mapper
public interface SysDeptRelationMapper extends ExtendBaseMapper<SysDeptRelation> {

    /**
     * 删除部门关系表数据
     *
     * @param id 部门ID
     */
    void deleteDeptRelationsById(Long id);

    /**
     * 更改部分关系表数据
     *
     * @param deptRelation
     */
    void updateDeptRelations(SysDeptRelation deptRelation);

}
