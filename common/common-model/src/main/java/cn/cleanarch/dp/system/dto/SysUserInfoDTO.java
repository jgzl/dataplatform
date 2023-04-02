package cn.cleanarch.dp.system.dto;

import cn.cleanarch.dp.system.vo.user.SysUserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserInfoDTO extends SysUserVO implements Serializable {

    /**
     * 权限标识集合
     */
    private List<String> permissions;

    /**
     * 角色集合
     */
    private List<String> roles;

}
