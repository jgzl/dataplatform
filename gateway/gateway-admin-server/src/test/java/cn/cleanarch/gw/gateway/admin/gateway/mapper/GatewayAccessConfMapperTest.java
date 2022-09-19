package cn.cleanarch.gw.gateway.admin.gateway.mapper;

import cn.cleanarch.gw.common.core.constant.CommonConstants;
import cn.cleanarch.gw.common.core.constant.enums.StatusEnum;
import cn.cleanarch.gw.common.model.gateway.domain.GatewayAccessConf;
import cn.cleanarch.gw.common.test.core.ut.BaseDbUnitTest;
import cn.cleanarch.gw.common.test.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GatewayAccessConfMapperTest extends BaseDbUnitTest {

    @Resource
    private GatewayAccessConfMapper gatewayAccessConfMapper;

    @Test
    public void test_insertBatch_success() {
        List<GatewayAccessConf> saveDOList = RandomUtil.randomPojoList(GatewayAccessConf.class, e -> {
            e.setStatus(StatusEnum.ENABLE.getCode());
            e.setDeleted(CommonConstants.STATUS_NORMAL);
        });
        gatewayAccessConfMapper.insertBatch(saveDOList);
        List<Long> idList = saveDOList.stream().map(GatewayAccessConf::getId).collect(Collectors.toList());
        List<GatewayAccessConf> findDOList = gatewayAccessConfMapper.selectBatchIds(idList);
        assertEquals(saveDOList.size(), findDOList.size());
    }

}