package cn.cleanarch.dp.gateway.spi.log.service;

import cn.cleanarch.dp.common.core.constant.ServiceNameConstants;
import cn.cleanarch.dp.common.core.utils.JacksonUtil;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.admin.vo.GatewayLogVO;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/22
 */
@Slf4j
@RequiredArgsConstructor
@Service("localFileGatewayLogService")
public class LocalFileGatewayLogServiceImpl implements GatewayLogService {

    private final Environment environment;

    @Override
    public Page<GatewayLogVO> getByGatewayRequestLog(Page<GatewayLogVO> page, GatewayLogVO gatewayRequestLog) {
        String prefixPath = environment.getProperty("logging.prefix");
        Reader reader = FileUtil.getUtf8Reader(prefixPath + "/" + ServiceNameConstants.GATEWAY_SERVICE + "/gateway.log");
        List<String> lineList = ListUtil.toList();
        IoUtil.readLines(reader, lineList);
        List<GatewayLogVO> result = lineList.stream().map(line -> JacksonUtil.parseObject(line, GatewayLogVO.class)).skip((page.getCurrent() - 1) * page.getSize()).limit(page.getSize()).collect(Collectors.toList());
        page.setRecords(result);
        page.setTotal(lineList.size());
        return page;
    }

    @Override
    public List<GatewayLogDO> findAll() {
        return Lists.newArrayList();
    }

    @Override
    public List<GatewayLogDO> findAllById(List<String> idList) {
        return Lists.newArrayList();
    }

    @Override
    public List<GatewayLogDO> saveAll(List<GatewayLogDO> list) {
        return list;
    }

    @Override
    public GatewayLogDO save(GatewayLogDO domain) {
        return null;
    }

    @Override
    public void deleteAll() {
        log.info("删除数据");
    }

    @Override
    public void deleteAllById(List<String> idList) {
        log.info("删除数据");
    }
}
