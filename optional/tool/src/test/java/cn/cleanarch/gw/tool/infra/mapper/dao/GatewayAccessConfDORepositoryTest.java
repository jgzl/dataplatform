package cn.cleanarch.gw.tool.infra.mapper.dao;

import cn.cleanarch.gw.common.test.core.util.RandomUtil;
import cn.cleanarch.gw.tool.infra.mapper.dataobject.GatewayAccessConfDO;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@ActiveProfiles("unit-test") // 设置使用 application-unit-test 配置文件
@Sql(scripts = "classpath:/sql/create_tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
// 每个单元测试结束后，清理 DB
@Sql(scripts = "classpath:/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // 每个单元测试结束后，清理 DB
public class GatewayAccessConfDORepositoryTest {
    @Autowired
    private GatewayAccessConfDORepository repository;

    @Test
    public void test_FindAll_success() {
        List<GatewayAccessConfDO> gatewayAccessConfDOS = RandomUtil.randomPojoList(GatewayAccessConfDO.class, conf -> {
            conf.setId(null);
        });
        List<GatewayAccessConfDO> saveDOList = repository.saveAll(gatewayAccessConfDOS);
        List<GatewayAccessConfDO> findDOList = repository.findAll();
        saveDOList.forEach(e -> log.info("saveDOList:" + JSONUtil.toJsonStr(e)));
        findDOList.forEach(e -> log.info("findDOList:" + JSONUtil.toJsonStr(e)));
        assertEquals(saveDOList.size(), findDOList.size());
    }
}