package cn.cleanarch.dp.system.sys.service.dept;

import cn.cleanarch.dp.system.sys.dataobject.dept.SysDeptDO;
import cn.cleanarch.dp.system.sys.dataobject.dept.SysDeptRelationDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2018-02-12
 */
public interface SysDeptRelationService extends IService<SysDeptRelationDO> {

    /**
     * 新建部门关系
     *
     * @param sysDeptDO 部门
     */
    void insertDeptRelation(SysDeptDO sysDeptDO);

    /**
     * 通过ID删除部门关系
     *
     * @param id
     */
    void deleteAllDeptRelation(String id);

    /**
     * 更新部门关系
     *
     * @param relation
     */
    void updateDeptRelation(SysDeptRelationDO relation);

}
