package cn.cleanarch.dp.gateway.vo;

import cn.cleanarch.dp.gateway.domain.GatewayLogDO;
import lombok.Data;

/**
 * 网关日志Vo
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/16
 */
@Data
public class GatewayLogVO extends GatewayLogDO {

    /**
     * 创建时间
     */
    private String[] createTimeRange;

    /**
     * 更新时间
     */
    private String[] updateTimeRange;
}
