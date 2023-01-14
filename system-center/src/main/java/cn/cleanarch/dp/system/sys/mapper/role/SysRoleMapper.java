package cn.cleanarch.dp.system.sys.mapper.role;

import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.system.sys.dataobject.role.SysRoleDO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
public interface SysRoleMapper extends ExtendBaseMapper<SysRoleDO> {

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRoleDO> listRolesByUserId(String userId);

}
