package cn.cleanarch.dp.gateway.admin.mapper;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.core.constant.enums.StatusEnum;
import cn.cleanarch.dp.common.test.core.ut.BaseDbUnitTest;
import cn.cleanarch.dp.common.test.core.util.RandomUtil;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayAccessDO;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GatewayAccessDOMapperTest extends BaseDbUnitTest {

    @Resource
    private GatewayAccessMapper gatewayAccessMapper;

    @Test
    public void test_insertBatch_success() {
        List<GatewayAccessDO> saveDOList = RandomUtil.randomPojoList(GatewayAccessDO.class, e -> {
            e.setStatus(StatusEnum.ENABLE.getCode());
            e.setDeleted(CommonConstants.STATUS_NORMAL);
        });
        gatewayAccessMapper.insertBatch(saveDOList);
        List<String> idList = saveDOList.stream().map(GatewayAccessDO::getId).collect(Collectors.toList());
        List<GatewayAccessDO> findDOList = gatewayAccessMapper.selectBatchIds(idList);
        assertEquals(saveDOList.size(), findDOList.size());
    }

}