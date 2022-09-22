package cn.cleanarch.gw.common.security.service;

import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.gateway.admin.system.domain.SysUserDO;
import cn.cleanarch.gw.gateway.admin.system.dto.SysUserDTO;
import cn.cleanarch.gw.gateway.admin.system.dto.SysUserInfoDTO;
import cn.cleanarch.gw.gateway.admin.system.vo.SysUserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author li7hai26@gmail.com
 * @date 2017/10/31
 */
public interface SysUserService extends IService<SysUserDO> {

    /**
     * 查询用户信息
     *
     * @param userName 用户
     * @return userInfo
     */
    SysUserInfoDTO findUserInfo(String userName);

    /**
     * 查询用户信息
     *
     * @param sysUserDO 用户
     * @return userInfo
     */
    SysUserInfoDTO findUserInfo(SysUserDO sysUserDO);

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    IPage getUsersWithDeptPage(Page page, SysUserDTO userDTO);

    /**
     * 删除用户
     *
     * @param sysUserDO 用户
     * @return boolean
     */
    Boolean deleteUserById(SysUserDO sysUserDO);

    /**
     * 更新当前用户基本信息
     *
     * @param userDto 用户信息
     * @return Boolean
     */
    R<Boolean> updateUserInfo(SysUserDTO userDto);

    /**
     * 更新指定用户信息
     *
     * @param userDto 用户信息
     * @return
     */
    Boolean updateUser(SysUserDTO userDto);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    SysUserVO selectUserVoById(Integer id);

    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    List<SysUserDO> listAncestorUsers(String username);

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    Boolean saveUser(SysUserDTO userDto);

    List<SysUserVO> getUsersWithDept(SysUserDTO userDTO);

    Boolean updateUserForLockFlag(SysUserDTO userDto);
}
