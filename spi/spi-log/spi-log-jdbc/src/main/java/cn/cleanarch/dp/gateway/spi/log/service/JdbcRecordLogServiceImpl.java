package cn.cleanarch.dp.gateway.spi.log.service;

import cn.cleanarch.dp.common.core.spi.Join;
import cn.cleanarch.dp.gateway.dataobject.GatewayLogDO;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author li7hai26@gmail.com
 */
@Slf4j
@Join
public class JdbcRecordLogServiceImpl implements RecordLogService {
    @Override
    public void recordLog(GatewayLogDO gatewayLog) {
        GatewayLogService service = SpringUtil.getBean(JdbcGatewayLogServiceImpl.class);
        try {
            service.save(gatewayLog);
            log.info("日志id为[{}],入库成功", gatewayLog.getId());
        }catch (Exception e) {
            log.error("日志id为[{}],入库异常:", gatewayLog.getId(),e);
        }
    }
}
