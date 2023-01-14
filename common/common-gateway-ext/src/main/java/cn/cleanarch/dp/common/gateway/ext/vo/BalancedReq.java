package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.Balanced;
import cn.cleanarch.dp.common.gateway.ext.dataobject.LoadServer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description
 * @Author jianglong
 * @Date 2020/06/28
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BalancedReq extends Balanced implements java.io.Serializable {
    private Integer currentPage;
    private Integer pageSize;
    private List<LoadServer> serverList;
}
