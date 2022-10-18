package cn.cleanarch.dp.system.domain;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 部门关系表
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@TableName("sys_dept_relation")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysDeptRelationDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 祖先节点
     */
    private String ancestor;

    /**
     * 后代节点
     */
    private String descendant;

}
