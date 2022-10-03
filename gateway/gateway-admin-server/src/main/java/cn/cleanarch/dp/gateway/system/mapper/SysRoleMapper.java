package cn.cleanarch.dp.gateway.system.mapper;

import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.system.domain.SysRoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
@Mapper
public interface SysRoleMapper extends ExtendBaseMapper<SysRoleDO> {

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRoleDO> listRolesByUserId(String userId);

}
