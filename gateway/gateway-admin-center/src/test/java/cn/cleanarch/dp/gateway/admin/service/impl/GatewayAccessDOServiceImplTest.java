package cn.cleanarch.dp.gateway.admin.service.impl;

import cn.cleanarch.dp.common.core.constant.enums.StatusEnum;
import cn.cleanarch.dp.common.test.core.ut.BaseDbAndRedisUnitTest;
import cn.cleanarch.dp.common.test.core.util.RandomUtil;
import cn.cleanarch.dp.gateway.convert.GatewayAccessConfConvert;
import cn.cleanarch.dp.gateway.dataobject.GatewayAccessDO;
import cn.cleanarch.dp.gateway.vo.GatewayAccessVO;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import({GatewayAccessServiceImpl.class})
public class GatewayAccessDOServiceImplTest extends BaseDbAndRedisUnitTest {

    @Resource
    private GatewayAccessServiceImpl gatewayAccessConfService;

    @Test
    public void test_saveOrUpdate_success() {
        GatewayAccessDO saveDO = RandomUtil.randomPojo(GatewayAccessDO.class, conf -> {
            conf.setStatus(StatusEnum.ENABLE.getCode());
        });
        gatewayAccessConfService.saveOrUpdate(saveDO);
        GatewayAccessDO findDO = gatewayAccessConfService.getById(saveDO.getId());
        assertNotNull(findDO);
    }

    @Test
    public void test_updateStatus_success() {
        GatewayAccessDO saveDO = RandomUtil.randomPojo(GatewayAccessDO.class, conf -> {
            conf.setStatus(StatusEnum.ENABLE.getCode());
        });
        gatewayAccessConfService.saveOrUpdate(saveDO);
        GatewayAccessVO saveVO = GatewayAccessConfConvert.INSTANCE.convertDo2Vo(saveDO);
        gatewayAccessConfService.updateStatus(saveVO);
        GatewayAccessDO findDO = gatewayAccessConfService.getById(saveDO.getId());
        assertNotNull(findDO);
    }
}