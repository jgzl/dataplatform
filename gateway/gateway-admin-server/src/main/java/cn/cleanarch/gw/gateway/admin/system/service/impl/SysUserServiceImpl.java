package cn.cleanarch.gw.gateway.admin.system.service.impl;

import cn.cleanarch.gw.common.core.constant.CacheConstants;
import cn.cleanarch.gw.common.core.constant.CommonConstants;
import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.model.system.convert.UserConvert;
import cn.cleanarch.gw.common.model.system.domain.*;
import cn.cleanarch.gw.common.model.system.vo.SysUserDto;
import cn.cleanarch.gw.common.model.system.vo.SysUserVo;
import cn.cleanarch.gw.common.model.system.vo.UserInfo;
import cn.cleanarch.gw.common.security.service.SysUserService;
import cn.cleanarch.gw.gateway.admin.system.mapper.SysUserMapper;
import cn.cleanarch.gw.gateway.admin.system.service.*;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

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
    public Boolean saveUser(SysUserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDeleted(CommonConstants.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);
        List<SysUserRole> userRoleList = userDto.getRole().stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
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
    public UserInfo findUserInfo(String userName) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUserName, userName));
        if (sysUser == null) {
            return null;
        }
        return findUserInfo(sysUser);
    }

    /**
     * 通过查用户的全部信息
     *
     * @param sysUser 用户
     * @return
     */
    @Override
    public UserInfo findUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        SysUserVo sysUserVo = UserConvert.INSTANCE.convertDo2Vo(sysUser);
        userInfo.setSysUser(sysUserVo);
        // 查找当前用户对应角色
        List<SysRole> roleList = sysRoleService.findRolesByUserId(sysUser.getUserId());
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        roleList.forEach(role -> {
            roles.add(role.getRoleCode());
            List<String> permissionList = sysMenuService.findMenuByRoleId(role.getRoleId()).stream()
                    .map(SysMenu::getPermission).filter(StrUtil::isNotEmpty).toList();
            permissions.addAll(permissionList);
        });
        // 设置权限列表（menu.permission）
        userInfo.setRoles(Lists.newArrayList(roles));
        // 设置角色列表 （ID）
        userInfo.setPermissions(Lists.newArrayList(permissions));
        return userInfo;
    }

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    @Override
    public IPage getUsersWithDeptPage(Page page, SysUserDto userDTO) {
        return baseMapper.getUserVosPage(page, userDTO);
    }

    /**
     * 查询用户信息（含有角色信息）
     *
     * @param userDTO 参数列表
     * @return
     */
    @Override
    public List<SysUserVo> getUsersWithDept(SysUserDto userDTO) {
        return baseMapper.getUserVosPage(userDTO);
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public SysUserVo selectUserVoById(Integer id) {
        return baseMapper.getUserVoById(id);
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.userName")
    public Boolean deleteUserById(SysUser sysUser) {
        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        this.removeById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.userName")
    public R<Boolean> updateUserInfo(SysUserDto userDto) {
        SysUserVo sysUserVo = baseMapper.getUserVoByUsername(userDto.getUserName());
        if (!ENCODER.matches(userDto.getPassword(), sysUserVo.getPassword())) {
            log.info("原密码错误，修改个人信息失败:{}", userDto.getUserName());
            return R.error("原密码错误，修改个人信息失败");
        }

        SysUser sysUser = new SysUser();
        if (StrUtil.isNotBlank(userDto.getNewPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getNewPassword()));
        }
        sysUser.setMobile(userDto.getMobile());
        sysUser.setUserId(sysUserVo.getUserId());
        sysUser.setAvatar(userDto.getAvatar());
        return R.success(this.updateById(sysUser));
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.userName")
    public Boolean updateUser(SysUserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(userDto.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUser);

        sysUserRoleService
                .remove(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, userDto.getUserId()));
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            sysUserRoleService.save(userRole);
        });
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.userName")
    public Boolean updateUserForLockFlag(SysUserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());
        return this.updateById(sysUser);
    }

    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    @Override
    public List<SysUser> listAncestorUsers(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUserName, username));

        SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
        if (sysDept == null) {
            return null;
        }

        Long parentId = sysDept.getParentId();
        return this.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getDeptId, parentId));
    }

}
