package cn.cleanarch.dp.system.controller.errorcode;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.system.service.errorcode.SysErrorCodeService;
import cn.cleanarch.dp.system.convert.SysErrorCodeConvert;
import cn.cleanarch.dp.system.dataobject.errorcode.SysErrorCodeDO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeCreateReqVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodePageReqVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeRespVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeUpdateReqVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "系统管理-错误码")
@RestController
@RequestMapping("/error-code")
@Validated
public class SysErrorCodeController {

    @Resource
    private SysErrorCodeService errorCodeService;

    @PostMapping
    @ApiOperation("创建错误码")
    @PreAuthorize("@pms.hasPermission('system:error-code:create')")
    public R<String> createErrorCode(@Valid @RequestBody SysErrorCodeCreateReqVO createReqVO) {
        return R.success(errorCodeService.createErrorCode(createReqVO));
    }

    @PutMapping
    @ApiOperation("更新错误码")
    @PreAuthorize("@pms.hasPermission('system:error-code:update')")
    public R<Boolean> updateErrorCode(@Valid @RequestBody SysErrorCodeUpdateReqVO updateReqVO) {
        errorCodeService.updateErrorCode(updateReqVO);
        return R.success(true);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除错误码")
    @PreAuthorize("@pms.hasPermission('system:error-code:delete')")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public R<Boolean> deleteErrorCode(@PathVariable("id") String id) {
        errorCodeService.deleteErrorCode(id);
        return R.success(true);
    }

    @GetMapping
    @ApiOperation("获得错误码")
    @PreAuthorize("@pms.hasPermission('system:error-code:query')")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public R<SysErrorCodeRespVO> getErrorCode(@RequestParam("id") String id) {
        SysErrorCodeDO errorCode = errorCodeService.getErrorCode(id);
        return R.success(SysErrorCodeConvert.INSTANCE.convert(errorCode));
    }

    @GetMapping("/page")
    @ApiOperation("获得错误码分页")
    @PreAuthorize("@pms.hasPermission('system:error-code:query')")
    public R<IPage<SysErrorCodeRespVO>> getErrorCodePage(@Valid SysErrorCodePageReqVO pageVO) {
        IPage<SysErrorCodeDO> pageResult = errorCodeService.getErrorCodePage(pageVO);
        return R.success(SysErrorCodeConvert.INSTANCE.convertPage(pageResult));
    }
}
