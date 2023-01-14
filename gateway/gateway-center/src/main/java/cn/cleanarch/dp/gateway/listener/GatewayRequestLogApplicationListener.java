package cn.cleanarch.dp.gateway.listener;

import cn.cleanarch.dp.common.core.constant.enums.RecordLogEnum;
import cn.cleanarch.dp.common.core.spi.ExtensionLoader;
import cn.cleanarch.dp.gateway.configuration.properties.GatewayProperties;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.spi.log.service.RecordLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 监听器模式用于解耦日志过滤器和日志写入功能
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GatewayRequestLogApplicationListener implements ApplicationListener<GatewayRequestLogApplicationEvent> {

    private final GatewayProperties gatewayProperties;

    @Async
    @Override
    public void onApplicationEvent(GatewayRequestLogApplicationEvent event) {
         GatewayLogDO gatewayLog = event.getGatewayLog();
        log.info("开始日志入库");
        RecordLogService recordLogService;
        if (gatewayProperties.getLogType()==null) {
            recordLogService = ExtensionLoader.getExtensionLoader(RecordLogService.class).getJoin(RecordLogEnum.localfile.name());
        }else {
            recordLogService = ExtensionLoader.getExtensionLoader(RecordLogService.class).getJoin(gatewayProperties.getLogType().name());
        }
        recordLogService.recordLog(gatewayLog);
        log.info("结束日志入库");
    }
}
