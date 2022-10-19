package cn.cleanarch.dp.system.dto;

import cn.cleanarch.dp.system.vo.user.SysUserVO;
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
public class SysUserDTO extends SysUserVO {

    /**
     * 角色ID
     */
    private List<String> role;

    /**
     * 新密码
     */
    private String password2;

}
