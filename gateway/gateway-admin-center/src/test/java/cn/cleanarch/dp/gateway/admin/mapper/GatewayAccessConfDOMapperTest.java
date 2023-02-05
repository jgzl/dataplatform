package cn.cleanarch.dp.gateway.admin.mapper;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.core.constant.enums.StatusEnum;
import cn.cleanarch.dp.common.test.core.ut.BaseDbUnitTest;
import cn.cleanarch.dp.common.test.core.util.RandomUtil;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayAccessConfDO;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GatewayAccessConfDOMapperTest extends BaseDbUnitTest {

    @Resource
    private GatewayAccessMapper gatewayAccessMapper;

    @Test
    public void test_insertBatch_success() {
        List<GatewayAccessConfDO> saveDOList = RandomUtil.randomPojoList(GatewayAccessConfDO.class, e -> {
            e.setStatus(StatusEnum.ENABLE.getCode());
            e.setDeleted(CommonConstants.STATUS_NORMAL);
        });
        gatewayAccessMapper.insertBatch(saveDOList);
        List<String> idList = saveDOList.stream().map(GatewayAccessConfDO::getId).collect(Collectors.toList());
        List<GatewayAccessConfDO> findDOList = gatewayAccessMapper.selectBatchIds(idList);
        assertEquals(saveDOList.size(), findDOList.size());
    }

}