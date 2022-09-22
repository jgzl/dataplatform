package cn.cleanarch.gw.gateway.admin.gateway.service.impl;

import cn.cleanarch.gw.common.core.constant.enums.StatusEnum;
import cn.cleanarch.gw.common.test.core.ut.BaseDbAndRedisUnitTest;
import cn.cleanarch.gw.common.test.core.util.RandomUtil;
import cn.cleanarch.gw.gateway.admin.gateway.convert.GatewayAccessConfConvert;
import cn.cleanarch.gw.gateway.admin.gateway.domain.GatewayAccessConfDO;
import cn.cleanarch.gw.gateway.admin.gateway.vo.GatewayAccessConfVO;
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