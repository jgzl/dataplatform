package cn.cleanarch.gw.gateway.admin.system.controller;

import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.model.system.convert.ErrorCodeConvert;
import cn.cleanarch.gw.common.model.system.domain.ErrorCodeDO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodeCreateReqVO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodePageReqVO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodeRespVO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodeUpdateReqVO;
import cn.cleanarch.gw.gateway.admin.system.service.ErrorCodeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "管理后台 - 错误码")
@RestController
@RequestMapping("/system/error-code")
@Validated
public class ErrorCodeController {

    @Resource
    private ErrorCodeService errorCodeService;

    @PostMapping
    @ApiOperation("创建错误码")
//    @PreAuthorize("@pms.hasPermission('system:error-code:create')")
    public R<Long> createErrorCode(@Valid @RequestBody ErrorCodeCreateReqVO createReqVO) {
        return R.success(errorCodeService.createErrorCode(createReqVO));
    }

    @PutMapping
    @ApiOperation("更新错误码")
//    @PreAuthorize("@pms.hasPermission('system:error-code:update')")
    public R<Boolean> updateErrorCode(@Valid @RequestBody ErrorCodeUpdateReqVO updateReqVO) {
        errorCodeService.updateErrorCode(updateReqVO);
        return R.success(true);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除错误码")
//    @PreAuthorize("@pms.hasPermission('system:error-code:delete')")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public R<Boolean> deleteErrorCode(@PathVariable("id") Long id) {
        errorCodeService.deleteErrorCode(id);
        return R.success(true);
    }

    @GetMapping
    @ApiOperation("获得错误码")
//    @PreAuthorize("@pms.hasPermission('system:error-code:query')")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public R<ErrorCodeRespVO> getErrorCode(@RequestParam("id") Long id) {
        ErrorCodeDO errorCode = errorCodeService.getErrorCode(id);
        return R.success(ErrorCodeConvert.INSTANCE.convert(errorCode));
    }

    @GetMapping("/page")
    @ApiOperation("获得错误码分页")
//    @PreAuthorize("@pms.hasPermission('system:error-code:query')")
    public R<IPage<ErrorCodeRespVO>> getErrorCodePage(@Valid ErrorCodePageReqVO pageVO) {
        IPage<ErrorCodeDO> pageResult = errorCodeService.getErrorCodePage(pageVO);
        return R.success(ErrorCodeConvert.INSTANCE.convertPage(pageResult));
    }
}
