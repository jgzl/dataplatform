package cn.cleanarch.dp.gateway.system.service.impl;

import cn.cleanarch.dp.gateway.system.mapper.SysUserRoleMapper;
import cn.cleanarch.dp.gateway.system.service.SysUserRoleService;
import cn.cleanarch.dp.system.domain.SysUserRoleDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleDO> implements SysUserRoleService {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author 寻欢·李
     * @date 2017年12月7日 16:31:38
     */
    @Override
    public Boolean deleteByUserId(String userId) {
        return baseMapper.delete(Wrappers.lambdaQuery(SysUserRoleDO.class).eq(SysUserRoleDO::getUserId,userId))>0;
//        return baseMapper.deleteByUserId(userId);
    }

}
