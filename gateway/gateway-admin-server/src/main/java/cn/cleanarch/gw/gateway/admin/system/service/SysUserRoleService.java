package cn.cleanarch.gw.gateway.admin.system.service;

import cn.cleanarch.gw.gateway.admin.system.domain.SysUserRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
public interface SysUserRoleService extends IService<SysUserRoleDO> {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author 寻欢·李
     * @date 2017年12月7日 16:31:38
     */
    Boolean deleteByUserId(Long userId);

}
