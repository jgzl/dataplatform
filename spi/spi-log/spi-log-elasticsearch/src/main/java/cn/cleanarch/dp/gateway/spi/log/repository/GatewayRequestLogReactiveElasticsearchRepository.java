package cn.cleanarch.dp.gateway.spi.log.repository;

import cn.cleanarch.dp.gateway.dataobject.GatewayLogDO;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

/**
 * 网关日志持久化操作层
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/16
 */
public interface GatewayRequestLogReactiveElasticsearchRepository extends ReactiveElasticsearchRepository<GatewayLogDO, String> {

}
