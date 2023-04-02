package cn.cleanarch.dp.infra.controller.file;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigCreateReqVO;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigPageReqVO;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigRespVO;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigUpdateReqVO;
import cn.cleanarch.dp.infra.convert.file.FileConfigConvert;
import cn.cleanarch.dp.infra.dataobject.file.FileConfigDO;
import cn.cleanarch.dp.infra.service.file.FileConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.cleanarch.dp.common.core.model.R.success;

@Tag(name = "管理后台 - 文件配置")
@RestController
@RequestMapping("/infra/file-config")
@Validated
public class FileConfigController {

    @Resource
    private FileConfigService fileConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建文件配置")
    //@PreAuthorize("@pms.hasPermission('infra:file-config:create')")
    public R<String> createFileConfig(@Valid @RequestBody FileConfigCreateReqVO createReqVO) {
        return success(fileConfigService.createFileConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新文件配置")
    //@PreAuthorize("@pms.hasPermission('infra:file-config:update')")
    public R<Boolean> updateFileConfig(@Valid @RequestBody FileConfigUpdateReqVO updateReqVO) {
        fileConfigService.updateFileConfig(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-master")
    @Operation(summary = "更新文件配置为 Master")
    //@PreAuthorize("@pms.hasPermission('infra:file-config:update')")
    public R<Boolean> updateFileConfigMaster(@RequestParam("id") String id) {
        fileConfigService.updateFileConfigMaster(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除文件配置")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@pms.hasPermission('infra:file-config:delete')")
    public R<Boolean> deleteFileConfig(@RequestParam("id") String id) {
        fileConfigService.deleteFileConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得文件配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@pms.hasPermission('infra:file-config:query')")
    public R<FileConfigRespVO> getFileConfig(@RequestParam("id") String id) {
        FileConfigDO fileConfig = fileConfigService.getFileConfig(id);
        return success(FileConfigConvert.INSTANCE.convert(fileConfig));
    }

    @GetMapping("/page")
    @Operation(summary = "获得文件配置分页")
    //@PreAuthorize("@pms.hasPermission('infra:file-config:query')")
    public R<PageResult<FileConfigRespVO>> getFileConfigPage(@Valid FileConfigPageReqVO pageVO) {
        PageResult<FileConfigDO> pageResult = fileConfigService.getFileConfigPage(pageVO);
        return success(FileConfigConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/test")
    @Operation(summary = "测试文件配置是否正确")
    //@PreAuthorize("@pms.hasPermission('infra:file-config:query')")
    public R<String> testFileConfig(@RequestParam("id") String id) throws Exception {
        String url = fileConfigService.testFileConfig(id);
        return success(url);
    }
}
