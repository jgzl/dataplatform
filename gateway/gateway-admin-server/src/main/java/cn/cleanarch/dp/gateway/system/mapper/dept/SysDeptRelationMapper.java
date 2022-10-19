package cn.cleanarch.dp.gateway.system.mapper.dept;

import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.system.dataobject.dept.SysDeptRelationDO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2018-02-12
 */
public interface SysDeptRelationMapper extends ExtendBaseMapper<SysDeptRelationDO> {

    /**
     * 删除部门关系表数据
     *
     * @param id 部门ID
     */
    void deleteDeptRelationsById(String id);

    /**
     * 更改部分关系表数据
     *
     * @param deptRelation
     */
    void updateDeptRelations(SysDeptRelationDO deptRelation);

}
