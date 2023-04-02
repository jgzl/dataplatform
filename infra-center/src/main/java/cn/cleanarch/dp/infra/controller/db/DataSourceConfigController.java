package cn.cleanarch.dp.infra.controller.db;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigCreateReqVO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigRespVO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigUpdateReqVO;
import cn.cleanarch.dp.infra.convert.db.DataSourceConfigConvert;
import cn.cleanarch.dp.infra.dataobject.db.DataSourceConfigDO;
import cn.cleanarch.dp.infra.service.db.DataSourceConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.cleanarch.dp.common.core.model.R.success;

@Tag(name = "管理后台 - 数据源配置")
@RestController
@RequestMapping("/infra/data-source-config")
@Validated
public class DataSourceConfigController {

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建数据源配置")
    //@PreAuthorize("@pms.hasPermission('infra:data-source-config:create')")
    public R<String> createDataSourceConfig(@Valid @RequestBody DataSourceConfigCreateReqVO createReqVO) {
        return success(dataSourceConfigService.createDataSourceConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新数据源配置")
    //@PreAuthorize("@pms.hasPermission('infra:data-source-config:update')")
    public R<Boolean> updateDataSourceConfig(@Valid @RequestBody DataSourceConfigUpdateReqVO updateReqVO) {
        dataSourceConfigService.updateDataSourceConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除数据源配置")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@pms.hasPermission('infra:data-source-config:delete')")
    public R<Boolean> deleteDataSourceConfig(@RequestParam("id") Long id) {
        dataSourceConfigService.deleteDataSourceConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据源配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@pms.hasPermission('infra:data-source-config:query')")
    public R<DataSourceConfigRespVO> getDataSourceConfig(@RequestParam("id") String id) {
        DataSourceConfigDO dataSourceConfig = dataSourceConfigService.getDataSourceConfig(id);
        return success(DataSourceConfigConvert.INSTANCE.convert(dataSourceConfig));
    }

    @GetMapping("/list")
    @Operation(summary = "获得数据源配置列表")
    //@PreAuthorize("@pms.hasPermission('infra:data-source-config:query')")
    public R<List<DataSourceConfigRespVO>> getDataSourceConfigList() {
        List<DataSourceConfigDO> list = dataSourceConfigService.getDataSourceConfigList();
        return success(DataSourceConfigConvert.INSTANCE.convertList(list));
    }

}
