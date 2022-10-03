package cn.cleanarch.dp.gateway.admin.gateway.service.impl;

import cn.cleanarch.dp.common.core.constant.enums.StatusEnum;
import cn.cleanarch.dp.common.test.core.ut.BaseDbAndRedisUnitTest;
import cn.cleanarch.dp.common.test.core.util.RandomUtil;
import cn.cleanarch.dp.gateway.convert.GatewayAccessConfConvert;
import cn.cleanarch.dp.gateway.domain.GatewayAccessConfDO;
import cn.cleanarch.dp.gateway.vo.GatewayAccessConfVO;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import({GatewayAccessConfServiceImpl.class})
public class GatewayAccessConfDOServiceImplTest extends BaseDbAndRedisUnitTest {

    @Resource
    private GatewayAccessConfServiceImpl gatewayAccessConfService;

    @Test
    public void test_saveOrUpdate_success() {
        GatewayAccessConfDO saveDO = RandomUtil.randomPojo(GatewayAccessConfDO.class, conf -> {
            conf.setStatus(StatusEnum.ENABLE.getCode());
        });
        gatewayAccessConfService.saveOrUpdate(saveDO);
        GatewayAccessConfDO findDO = gatewayAccessConfService.getById(saveDO.getId());
        assertNotNull(findDO);
    }

    @Test
    public void test_updateStatus_success() {
        GatewayAccessConfDO saveDO = RandomUtil.randomPojo(GatewayAccessConfDO.class, conf -> {
            conf.setStatus(StatusEnum.ENABLE.getCode());
        });
        gatewayAccessConfService.saveOrUpdate(saveDO);
        GatewayAccessConfVO saveVO = GatewayAccessConfConvert.INSTANCE.convertDo2Vo(saveDO);
        gatewayAccessConfService.updateStatus(saveVO);
        GatewayAccessConfDO findDO = gatewayAccessConfService.getById(saveDO.getId());
        assertNotNull(findDO);
    }
}