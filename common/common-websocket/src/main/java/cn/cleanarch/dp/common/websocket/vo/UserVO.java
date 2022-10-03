package cn.cleanarch.dp.common.websocket.vo;

import lombok.Data;

/**
 * websocket用户信息
 */
@Data
public class UserVO {
    /**
     * 用户名/用户id
     */
    private String user;
    /**
     * 角色/角色id
     */
    private String role;
    /**
     * 租户/租户id
     */
    private String tenant;
}
