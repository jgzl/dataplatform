package cn.cleanarch.dp.system.dataobject.user;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

}
