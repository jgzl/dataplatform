package cn.cleanarch.gw.common.model.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

/**
 * 登陆用户
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
public class LoginUserVo {
    /**
     * 主键ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 别名
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 认证token
     */
    private String token;

    /**
     * 角色集合
     */
    private List<String> roles;

    /**
     * 权限集合
     */
    private List<String> permissions;

    private LoginUserInfo userInfo;
}
