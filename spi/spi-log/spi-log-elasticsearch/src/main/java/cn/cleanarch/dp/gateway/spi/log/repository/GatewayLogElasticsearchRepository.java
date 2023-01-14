package cn.cleanarch.dp.gateway.spi.log.repository;

import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 网关日志持久化操作层
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/16
 */
public interface GatewayLogElasticsearchRepository extends ElasticsearchRepository<GatewayLogDO, String> {

}
