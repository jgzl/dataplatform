package cn.cleanarch.dp.gateway.system.service.impl;

import cn.cleanarch.dp.common.test.core.ut.BaseDbAndRedisUnitTest;
import cn.cleanarch.dp.common.test.core.util.RandomUtil;
import cn.cleanarch.dp.system.sys.service.errorcode.SysErrorCodeServiceImpl;
import cn.cleanarch.dp.system.sys.dataobject.errorcode.SysErrorCodeDO;
import cn.cleanarch.dp.system.sys.vo.errorcode.SysErrorCodeCreateReqVO;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import({SysErrorCodeServiceImpl.class})
public class SysErrorCodeServiceImplTest extends BaseDbAndRedisUnitTest {

    @Resource
    private SysErrorCodeServiceImpl errorCodeService;

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