package cn.cleanarch.dp.gateway.system.service.impl;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.security.service.SysUserService;
import cn.cleanarch.dp.gateway.system.mapper.SysUserMapper;
import cn.cleanarch.dp.gateway.system.service.*;
import cn.cleanarch.dp.system.convert.SysUserConvert;
import cn.cleanarch.dp.system.domain.*;
import cn.cleanarch.dp.system.dto.SysUserDTO;
import cn.cleanarch.dp.system.dto.SysUserInfoDTO;
import cn.cleanarch.dp.system.vo.SysUserVO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author li7hai26@gmail.com
 * @date 2017/10/31
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final SysMenuService sysMenuService;

    private final SysRoleService sysRoleService;

    private final SysDeptService sysDeptService;

    private final SysUserRoleService sysUserRoleService;

    private final SysDeptRelationService sysDeptRelationService;

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(SysUserDTO userDto) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(userDto, sysUserDO);
        sysUserDO.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUserDO);
        List<SysUserRoleDO> userRoleList = userDto.getRole().stream().map(roleId -> {
            SysUserRoleDO userRole = new SysUserRoleDO();
            userRole.setUserId(sysUserDO.getId());
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());
        return sysUserRoleService.saveBatch(userRoleList);
    }

    /**
     * 通过查用户的全部信息
     *
     * @param userName 用户
     * @return
     */
    @Override
    public SysUserInfoDTO findUserInfo(String userName) {
        SysUserDO sysUserDO = this.getOne(Wrappers.<SysUserDO>query().lambda().eq(SysUserDO::getUserName, userName));
        if (sysUserDO == null) {
            return null;
        }
        return findUserInfo(sysUserDO);
    }

    /**
     * 通过查用户的全部信息
     *
     * @param sysUserDO 用户
     * @return
     */
    @Override
    public SysUserInfoDTO findUserInfo(SysUserDO sysUserDO) {
        SysUserInfoDTO sysUserInfoDTO = SysUserConvert.INSTANCE.convert(sysUserDO);
        // 查找当前用户对应角色
        List<SysRoleDO> roleList = sysRoleService.findRolesByUserId(sysUserDO.getId());
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        roleList.forEach(role -> {
            roles.add(role.getRoleCode());
            List<String> permissionList = sysMenuService.findMenuByRoleId(role.getId()).stream()
                    .map(SysMenuDO::getPermission).filter(StrUtil::isNotEmpty).toList();
            permissions.addAll(permissionList);
        });
        // 设置权限列表（menu.permission）
        sysUserInfoDTO.setRoles(Lists.newArrayList(roles));
        // 设置角色列表 （ID）
        sysUserInfoDTO.setPermissions(Lists.newArrayList(permissions));
        return sysUserInfoDTO;
    }

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    @Override
    public IPage getUsersWithDeptPage(Page page, SysUserDTO userDTO) {
        return baseMapper.getUserVosPage(page, userDTO);
    }

    /**
     * 查询用户信息（含有角色信息）
     *
     * @param userDTO 参数列表
     * @return
     */
    @Override
    public List<SysUserVO> getUsersWithDept(SysUserDTO userDTO) {
        return baseMapper.getUserVosPage(userDTO);
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public SysUserVO selectUserVoById(Integer id) {
        return baseMapper.getUserVoById(id);
    }

    /**
     * 删除用户
     *
     * @param sysUserDO 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUserDO.userName")
    public Boolean deleteUserById(SysUserDO sysUserDO) {
        sysUserRoleService.deleteByUserId(sysUserDO.getId());
        this.removeById(sysUserDO.getId());
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.userName")
    public R<Boolean> updateUserInfo(SysUserDTO userDto) {
        SysUserVO sysUserVo = baseMapper.getUserVoByUsername(userDto.getUserName());
        if (!ENCODER.matches(userDto.getPassword(), sysUserVo.getPassword())) {
            log.info("原密码错误，修改个人信息失败:{}", userDto.getUserName());
            return R.error("原密码错误，修改个人信息失败");
        }

        SysUserDO sysUserDO = new SysUserDO();
        if (StrUtil.isNotBlank(userDto.getPassword2())) {
            sysUserDO.setPassword(ENCODER.encode(userDto.getPassword2()));
        }
        sysUserDO.setMobile(userDto.getMobile());
        sysUserDO.setId(sysUserVo.getId());
        sysUserDO.setAvatar(userDto.getAvatar());
        return R.success(this.updateById(sysUserDO));
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.userName")
    public Boolean updateUser(SysUserDTO userDto) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(userDto, sysUserDO);
        sysUserDO.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(userDto.getPassword())) {
            sysUserDO.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUserDO);

        sysUserRoleService
                .remove(Wrappers.<SysUserRoleDO>update().lambda().eq(SysUserRoleDO::getUserId, userDto.getId()));
        userDto.getRole().forEach(roleId -> {
            SysUserRoleDO userRole = new SysUserRoleDO();
            userRole.setUserId(sysUserDO.getId());
            userRole.setRoleId(roleId);
            sysUserRoleService.save(userRole);
        });
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.userName")
    public Boolean updateUserForLockFlag(SysUserDTO userDto) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(userDto, sysUserDO);
        sysUserDO.setUpdateTime(LocalDateTime.now());
        return this.updateById(sysUserDO);
    }

    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    @Override
    public List<SysUserDO> listAncestorUsers(String username) {
        SysUserDO sysUserDO = this.getOne(Wrappers.<SysUserDO>query().lambda().eq(SysUserDO::getUserName, username));

        SysDeptDO sysDeptDO = sysDeptService.getById(sysUserDO.getDeptId());
        if (sysDeptDO == null) {
            return null;
        }

        String parentId = sysDeptDO.getParentId();
        return this.list(Wrappers.<SysUserDO>query().lambda().eq(SysUserDO::getDeptId, parentId));
    }

}
