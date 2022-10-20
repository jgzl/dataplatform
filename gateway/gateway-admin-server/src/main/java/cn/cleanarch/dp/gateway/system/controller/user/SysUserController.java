package cn.cleanarch.dp.gateway.system.controller.user;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.oauth.annotation.Inner;
import cn.cleanarch.dp.common.oauth.util.AppContextHolder;
import cn.cleanarch.dp.gateway.system.service.SysUserService;
import cn.cleanarch.dp.system.dataobject.user.SysUserDO;
import cn.cleanarch.dp.system.dto.SysUserDTO;
import cn.cleanarch.dp.system.dto.SysUserInfoDTO;
import cn.cleanarch.dp.system.vo.user.SysUserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用户管理模块
 *
 * @author li7hai26@gmail.com
 * @date 2018/12/16
 */
@Valid
@RestController
@AllArgsConstructor
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserService userService;

    /**
     * 获取指定用户全部信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    @Inner(value = false)
    @GetMapping("/info/{userName}")
    public R<SysUserInfoDTO> infoByUserName(@NotEmpty @PathVariable String userName) {
        return R.success(userService.findUserInfo(userName));
    }

    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/info"})
    @PreAuthorize("@pms.hasPermission('system:sys-user:query')")
    public R<SysUserInfoDTO> info() {
        return R.success(userService.findUserInfo(AppContextHolder.getUser().getUsername()));
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('system:sys-user:query')")
    public R<SysUserVO> queryUserById(@PathVariable String id) {
        return R.success(userService.selectUserVoById(id));
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return
     */
    @GetMapping("/details/{userName}")
    @PreAuthorize("@pms.hasPermission('system:sys-user:query')")
    public R<SysUserDO> queryUserByUserName(@PathVariable String userName) {
        SysUserDO condition = new SysUserDO();
        condition.setUserName(userName);
        return R.success(userService.getOne(new QueryWrapper<>(condition)));
    }

    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('system:sys-user:delete')")
    public R<Boolean> userDel(@PathVariable String id) {
        SysUserDO sysUserDO = userService.getById(id);
        return R.success(userService.deleteUserById(sysUserDO));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @PostMapping
    @PreAuthorize("@pms.hasPermission('system:sys-user:create')")
    public R<Boolean> user(@RequestBody SysUserDTO userDto) {
        return R.success(userService.saveUser(userDto));
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @PutMapping
    @PreAuthorize("@pms.hasPermission('system:sys-user:update')")
    public R<Boolean> updateUser(@Valid @RequestBody SysUserDTO userDto) {
        return R.success(userService.updateUser(userDto));
    }

    /**
     * 更新用户信息-锁定状态
     *
     * @param userDto 用户信息
     * @return R
     */
    @PutMapping("/lockFlag")
    @PreAuthorize("@pms.hasPermission('system:sys-user:update')")
    public R<Boolean> updateUserForLockFlag(@Valid @RequestBody SysUserDTO userDto) {
        return R.success(userService.updateUserForLockFlag(userDto));
    }

    /**
     * 分页查询用户
     *
     * @param page    参数集
     * @param userDTO 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('system:sys-user:query')")
    public R<IPage<SysUserVO>> getUserPage(Page page, SysUserDTO userDTO) {
        return R.success(userService.getUsersWithDeptPage(page, userDTO));
    }

    /**
     * 查询用户
     *
     * @param userDTO 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('system:sys-user:query')")
    public R<List<SysUserVO>> getUserList(SysUserDTO userDTO) {
        return R.success(userService.getUsersWithDept(userDTO));
    }

    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @return success/false
     */
    @PutMapping("/edit")
    @PreAuthorize("@pms.hasPermission('system:sys-user:update')")
    public R<Boolean> updateUserInfo(@Valid @RequestBody SysUserDTO userDto) {
        return userService.updateUserInfo(userDto);
    }

    /**
     * 上级部门用户列表
     *
     * @param userName 用户名称
     * @return
     */
    @GetMapping("/ancestor/{userName}")
    @PreAuthorize("@pms.hasPermission('system:sys-user:query')")
    public R<List<SysUserDO>> listAncestorUsers(@PathVariable String userName) {
        return R.success(userService.listAncestorUsers(userName));
    }

}
