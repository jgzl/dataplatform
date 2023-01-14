package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.Balanced;
import cn.cleanarch.dp.common.gateway.ext.dataobject.LoadServer;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author jianglong
 * @Date 2020/06/30
 * @Version V1.0
 */
@Data
public class BalancedRsp implements java.io.Serializable {
    private Balanced balanced;
    private List<LoadServer> serverList;
}
