package cn.cleanarch.dp.gateway.admin.system.service.impl;

import cn.cleanarch.dp.common.test.core.ut.BaseDbAndRedisUnitTest;
import cn.cleanarch.dp.common.test.core.util.RandomUtil;
import cn.cleanarch.dp.gateway.system.service.impl.ErrorCodeServiceImpl;
import cn.cleanarch.dp.system.domain.SysErrorCodeDO;
import cn.cleanarch.dp.system.vo.SysErrorCodeCreateReqVO;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import({ErrorCodeServiceImpl.class})
public class ErrorCodeServiceImplTest extends BaseDbAndRedisUnitTest {

    @Resource
    private ErrorCodeServiceImpl errorCodeService;

    @Test
    public void createErrorCode() {
        SysErrorCodeCreateReqVO reqVO = RandomUtil.randomPojo(SysErrorCodeCreateReqVO.class);
        String id = errorCodeService.createErrorCode(reqVO);
        SysErrorCodeDO findDO = errorCodeService.getErrorCode(id);
        assertNotNull(findDO);
    }

    @Test
    void updateErrorCode() {
    }

    @Test
    void deleteErrorCode() {
    }

    @Test
    void getErrorCode() {
    }

    @Test
    void getErrorCodePage() {
    }

    @Test
    void getErrorCodeList() {
    }

    @Test
    void autoGenerateErrorCodes() {
    }

    @Test
    void testGetErrorCodeList() {
    }
}