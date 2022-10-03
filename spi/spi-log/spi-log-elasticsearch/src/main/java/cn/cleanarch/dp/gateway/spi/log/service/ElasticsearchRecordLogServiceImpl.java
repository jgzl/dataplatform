package cn.cleanarch.dp.gateway.spi.log.service;

import cn.cleanarch.dp.common.core.spi.Join;
import cn.cleanarch.dp.gateway.domain.GatewayLogDO;
import cn.cleanarch.dp.gateway.spi.log.repository.GatewayRequestLogReactiveElasticsearchRepository;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Join
public class ElasticsearchRecordLogServiceImpl implements RecordLogService {
    @Override
    public void recordLog(GatewayLogDO gatewayLog) {
        GatewayRequestLogReactiveElasticsearchRepository repository = SpringUtil.getBean(GatewayRequestLogReactiveElasticsearchRepository.class);
        Mono<GatewayLogDO> logMono = repository.save(gatewayLog);
        logMono
                .doOnSuccess(s -> log.info("日志id为[{}],入库成功", gatewayLog.getId()))
                .doOnError(e -> log.error("日志id为[{}],入库异常:", gatewayLog.getId(), e))
                .subscribe();
    }
}
