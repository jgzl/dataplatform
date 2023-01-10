package cn.cleanarch.dp.gateway.spi.log.service;

import cn.cleanarch.dp.gateway.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.vo.GatewayLogVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/22
 */
public interface GatewayLogService {
    Page<GatewayLogVO> getByGatewayRequestLog(Page<GatewayLogVO> page, GatewayLogVO gatewayRequestLog);

    List<GatewayLogDO> findAll();

    List<GatewayLogDO> findAllById(List<String> idList);

    List<GatewayLogDO> saveAll(List<GatewayLogDO> list);

    GatewayLogDO save(GatewayLogDO domain);

    void deleteAll();

    void deleteAllById(List<String> idList);
}
