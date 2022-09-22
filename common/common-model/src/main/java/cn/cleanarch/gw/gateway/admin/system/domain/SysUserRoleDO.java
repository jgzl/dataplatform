package cn.cleanarch.gw.gateway.admin.system.domain;

import cn.cleanarch.gw.common.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 用户角色
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@TableName("sys_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUserRoleDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
