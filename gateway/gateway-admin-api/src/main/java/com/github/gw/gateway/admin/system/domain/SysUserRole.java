package com.github.gw.gateway.admin.system.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends Model<SysUserRole> {

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
