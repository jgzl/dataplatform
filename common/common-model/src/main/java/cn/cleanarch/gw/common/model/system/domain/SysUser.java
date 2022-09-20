package cn.cleanarch.gw.common.model.system.domain;

import cn.cleanarch.gw.common.model.base.BaseDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
public class SysUser extends BaseDO {

    private static final long serialVersionUID = 1L;
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
     * 随机盐
     */
    @JsonIgnore
    private String salt;

    /**
     * 锁定标记
     */
    private String lockFlag;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 上次登陆IP
     */
    private String lastLoginIp;

}
