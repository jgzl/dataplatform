package cn.cleanarch.dp.system.vo.user;

import cn.cleanarch.dp.system.vo.role.SysRoleVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author li7hai26@outlook.com
 * @date 2017/10/29
 */
@Data
public class SysUserVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

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
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 0-正常，1-删除
     */
    private String deleted;

    /**
     * 角色列表
     */
    private List<SysRoleVO> roleList;

}
