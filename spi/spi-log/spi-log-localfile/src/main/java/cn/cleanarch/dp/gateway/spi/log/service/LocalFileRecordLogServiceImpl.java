package cn.cleanarch.dp.gateway.spi.log.service;

import cn.cleanarch.dp.common.core.spi.Join;
import cn.cleanarch.dp.common.core.utils.JacksonUtil;
import cn.cleanarch.dp.gateway.domain.GatewayLogDO;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "gateway")
@Join
public class LocalFileRecordLogServiceImpl implements RecordLogService {
    @Override
    public void recordLog(GatewayLogDO gatewayLog) {
        log.info(JacksonUtil.toJsonString(gatewayLog));
    }
}
