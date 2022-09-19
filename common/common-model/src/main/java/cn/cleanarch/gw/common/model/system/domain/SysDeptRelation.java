package cn.cleanarch.gw.common.model.system.domain;

import cn.cleanarch.gw.common.model.base.BaseDO;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门关系表
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptRelation extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 祖先节点
     */
    private Long ancestor;

    /**
     * 后代节点
     */
    private Long descendant;

}
