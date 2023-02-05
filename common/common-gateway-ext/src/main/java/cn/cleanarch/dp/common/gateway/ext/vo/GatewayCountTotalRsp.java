package cn.cleanarch.dp.common.gateway.ext.vo;

import lombok.Data;

/**
 * @Description 封装按天和按小时的统计维度数据
 * @Author JL
 * @Date 2020/12/30
 * @Version V1.0
 */
@Data
public class GatewayCountTotalRsp implements java.io.Serializable {

    private GatewayCountRsp dayCounts;
    private GatewayCountRsp hourCounts;
    private GatewayCountRsp minCounts;

}
