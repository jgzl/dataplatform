package cn.cleanarch.dp.gateway.spi.log.service;

import cn.cleanarch.dp.gateway.admin.mapper.GatewayLogMapper;
import cn.cleanarch.dp.gateway.admin.convert.GatewayLogConvert;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.admin.vo.GatewayLogVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/22
 */
@Slf4j
@RequiredArgsConstructor
@Service("jdbcGatewayLogService")
public class JdbcGatewayLogServiceImpl implements GatewayLogService {

    private final GatewayLogMapper repository;

    @Override
    public Page<GatewayLogVO> getByGatewayRequestLog(Page<GatewayLogVO> page, GatewayLogVO gatewayRequestLog) {
        GatewayLogDO gatewayLog = GatewayLogConvert.INSTANCE.convertVo2Do(gatewayRequestLog);
        Page<GatewayLogDO> domainPage = GatewayLogConvert.INSTANCE.convertVo2Do(page);
        domainPage = repository.selectPage(domainPage, Wrappers.<GatewayLogDO>lambdaQuery().setEntity(gatewayLog));
        page = GatewayLogConvert.INSTANCE.convertDo2Vo(domainPage);
        return page;
    }

    @Override
    public List<GatewayLogDO> findAll() {
        return repository.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public List<GatewayLogDO> findAllById(List<String> idList) {
        return repository.selectList(Wrappers.lambdaQuery(GatewayLogDO.class).in(GatewayLogDO::getId,idList));
    }

    @Override
    public GatewayLogDO save(GatewayLogDO gatewayLog) {
        repository.insert(gatewayLog);
        return gatewayLog;
    }

    @Override
    public List<GatewayLogDO> saveAll(List<GatewayLogDO> list) {
        list.forEach(repository::insert);
        return list;
    }

    @Override
    public void deleteAll() {
        repository.delete(Wrappers.emptyWrapper());
    }

    @Override
    public void deleteAllById(List<String> idList) {
        repository.deleteBatchIds(idList);
    }
}
