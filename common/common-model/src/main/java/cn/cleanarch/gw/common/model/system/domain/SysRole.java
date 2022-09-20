package cn.cleanarch.gw.common.model.system.domain;

import cn.cleanarch.gw.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 角色
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色标识
     */
    @NotBlank(message = "角色标识不能为空")
    private String roleCode;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 数据权限类型
     */
//    @NotNull(message = "数据权限类型不能为空")
    private Integer dsType;

    /**
     * 数据权限作用范围
     */
    private String dsScope;

}