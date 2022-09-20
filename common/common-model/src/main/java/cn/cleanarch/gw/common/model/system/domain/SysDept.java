package cn.cleanarch.gw.common.model.system.domain;

import cn.cleanarch.gw.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 部门
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String name;

    /**
     * 排序
     */
//    @NotNull(message = "排序值不能为空")
    private Integer sort;

    /**
     * 父级部门id
     */
    private Long parentId;

    /**
     * 状态:0 禁用 1正常
     */
//    @NotNull(message = "状态不能为空")
    private Integer status;

}