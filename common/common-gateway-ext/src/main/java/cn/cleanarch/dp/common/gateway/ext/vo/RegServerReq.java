package cn.cleanarch.dp.common.gateway.ext.vo;

import cn.cleanarch.dp.common.gateway.ext.dataobject.RegServer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author jianglong
 * @Date 2020/05/16
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RegServerReq extends RegServer implements java.io.Serializable {
    private Integer currentPage;
    private Integer pageSize;
}
