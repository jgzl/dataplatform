package cn.cleanarch.gw.gateway.admin.system.service;

import cn.cleanarch.gw.common.model.system.domain.ErrorCodeDO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodeCreateReqVO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodeExportReqVO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodePageReqVO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodeUpdateReqVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.Valid;
import java.util.List;

/**
 * 错误码 Service 接口
 *
 * @author lihaifeng
 */
public interface ErrorCodeService extends ErrorCodeFrameworkService {

    /**
     * 创建错误码
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createErrorCode(@Valid ErrorCodeCreateReqVO createReqVO);

    /**
     * 更新错误码
     *
     * @param updateReqVO 更新信息
     */
    void updateErrorCode(@Valid ErrorCodeUpdateReqVO updateReqVO);

    /**
     * 删除错误码
     *
     * @param id 编号
     */
    void deleteErrorCode(Long id);

    /**
     * 获得错误码
     *
     * @param id 编号
     * @return 错误码
     */
    ErrorCodeDO getErrorCode(Long id);

    /**
     * 获得错误码分页
     *
     * @param pageReqVO 分页查询
     * @return 错误码分页
     */
    IPage<ErrorCodeDO> getErrorCodePage(ErrorCodePageReqVO pageReqVO);

    /**
     * 获得错误码列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 错误码列表
     */
    List<ErrorCodeDO> getErrorCodeList(ErrorCodeExportReqVO exportReqVO);

}
