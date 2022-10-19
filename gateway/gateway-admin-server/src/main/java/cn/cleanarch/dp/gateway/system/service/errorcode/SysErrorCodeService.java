package cn.cleanarch.dp.gateway.system.service.errorcode;

import cn.cleanarch.dp.system.dataobject.errorcode.SysErrorCodeDO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeCreateReqVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeExportReqVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodePageReqVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeUpdateReqVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.Valid;
import java.util.List;

/**
 * 错误码 Service 接口
 *
 * @author lihaifeng
 */
public interface SysErrorCodeService extends ErrorCodeFrameworkService {

    /**
     * 创建错误码
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createErrorCode(@Valid SysErrorCodeCreateReqVO createReqVO);

    /**
     * 更新错误码
     *
     * @param updateReqVO 更新信息
     */
    void updateErrorCode(@Valid SysErrorCodeUpdateReqVO updateReqVO);

    /**
     * 删除错误码
     *
     * @param id 编号
     */
    void deleteErrorCode(String id);

    /**
     * 获得错误码
     *
     * @param id 编号
     * @return 错误码
     */
    SysErrorCodeDO getErrorCode(String id);

    /**
     * 获得错误码分页
     *
     * @param pageReqVO 分页查询
     * @return 错误码分页
     */
    IPage<SysErrorCodeDO> getErrorCodePage(SysErrorCodePageReqVO pageReqVO);

    /**
     * 获得错误码列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 错误码列表
     */
    List<SysErrorCodeDO> getErrorCodeList(SysErrorCodeExportReqVO exportReqVO);

}
