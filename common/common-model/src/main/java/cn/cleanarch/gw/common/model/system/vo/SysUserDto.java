package cn.cleanarch.gw.common.model.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统用户传输对象
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserDto extends SysUserVo {

    /**
     * 角色ID
     */
    private List<Long> role;

    /**
     * 新密码
     */
    private String password2;

    /**
     * 新密码
     */
    private String newPassword;

}
