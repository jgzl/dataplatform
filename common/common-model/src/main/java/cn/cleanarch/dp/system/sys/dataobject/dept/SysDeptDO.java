package cn.cleanarch.dp.system.sys.dataobject.dept;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 部门
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@TableName("sys_dept")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysDeptDO extends BaseDO {

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
    private String parentId;

    /**
     * 状态:0 禁用 1正常
     */
//    @NotNull(message = "状态不能为空")
    private Integer status;

}