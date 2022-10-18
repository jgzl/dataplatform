package cn.cleanarch.dp.system.vo;

import lombok.Data;

import java.util.List;

/**
 * 登陆用户
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
public class LoginUserVO {
    /**
     * 主键ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 别名
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 认证token
     */
    private String token;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 角色集合
     */
    private List<String> roles;

    /**
     * 权限集合
     */
    private List<String> permissions;

    /**
     * 驾驶舱类型
     */
    private String dashboard = "0";
}
