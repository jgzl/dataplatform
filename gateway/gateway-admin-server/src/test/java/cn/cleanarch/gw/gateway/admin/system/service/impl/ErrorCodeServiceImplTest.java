package cn.cleanarch.gw.gateway.admin.system.service.impl;

import cn.cleanarch.gw.common.model.system.domain.ErrorCodeDO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodeCreateReqVO;
import cn.cleanarch.gw.common.test.core.ut.BaseDbAndRedisUnitTest;
import cn.cleanarch.gw.common.test.core.util.RandomUtil;
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
        ErrorCodeCreateReqVO reqVO = RandomUtil.randomPojo(ErrorCodeCreateReqVO.class);
        Long id = errorCodeService.createErrorCode(reqVO);
        ErrorCodeDO findDO = errorCodeService.getErrorCode(id);
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