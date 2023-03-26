package cn.cleanarch.dp.gateway.service;

import cn.cleanarch.dp.gateway.service.GatewayLogService;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.listener.GatewayRequestLogApplicationEvent;
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
    private final GatewayLogService gatewayLogService;

    @Async
    @Override
    public void onApplicationEvent(GatewayRequestLogApplicationEvent event) {
        GatewayLogDO gatewayLog = event.getGatewayLog();
        log.info("开始日志入库");
        gatewayLogService.save(gatewayLog);
        log.info("日志id为[{}],入库成功", gatewayLog.getId());
        log.info("结束日志入库");
    }
}
