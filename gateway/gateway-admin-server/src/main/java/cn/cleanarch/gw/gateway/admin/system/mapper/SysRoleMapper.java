package cn.cleanarch.gw.gateway.admin.system.mapper;

import cn.cleanarch.gw.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.gw.gateway.admin.system.domain.SysRoleDO;
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
    List<SysRoleDO> listRolesByUserId(Long userId);

}
