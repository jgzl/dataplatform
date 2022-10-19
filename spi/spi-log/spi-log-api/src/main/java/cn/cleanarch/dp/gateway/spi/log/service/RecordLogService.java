package cn.cleanarch.dp.gateway.spi.log.service;

import cn.cleanarch.dp.common.core.spi.SPI;
import cn.cleanarch.dp.gateway.domain.GatewayLogDO;

@SPI
public interface RecordLogService {
    void recordLog(GatewayLogDO gatewayLog);
}