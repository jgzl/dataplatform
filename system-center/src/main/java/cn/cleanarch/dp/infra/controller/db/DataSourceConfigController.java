package cn.cleanarch.dp.infra.controller.db;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.infra.service.db.DataSourceConfigService;
import cn.cleanarch.dp.infra.convert.db.DataSourceConfigConvert;
import cn.cleanarch.dp.infra.dataobject.db.DataSourceConfigDO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigCreateReqVO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigRespVO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "管理后台 - 数据源配置")
@RestController
@RequestMapping("/infra/data-source-config")
@Validated
public class DataSourceConfigController {

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @PostMapping("/create")
    @ApiOperation("创建数据源配置")
    @PreAuthorize("@pms.hasPermission('infra:data-source-config:create')")
    public R<String> createDataSourceConfig(@Valid @RequestBody DataSourceConfigCreateReqVO createReqVO) {
        return R.success(dataSourceConfigService.createDataSourceConfig(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新数据源配置")
    @PreAuthorize("@pms.hasPermission('infra:data-source-config:update')")
    public R<Boolean> updateDataSourceConfig(@Valid @RequestBody DataSourceConfigUpdateReqVO updateReqVO) {
        dataSourceConfigService.updateDataSourceConfig(updateReqVO);
        return R.success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除数据源配置")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@pms.hasPermission('infra:data-source-config:delete')")
    public R<Boolean> deleteDataSourceConfig(@RequestParam("id") Long id) {
        dataSourceConfigService.deleteDataSourceConfig(id);
        return R.success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得数据源配置")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@pms.hasPermission('infra:data-source-config:query')")
    public R<DataSourceConfigRespVO> getDataSourceConfig(@RequestParam("id") Long id) {
        DataSourceConfigDO dataSourceConfig = dataSourceConfigService.getDataSourceConfig(id);
        return R.success(DataSourceConfigConvert.INSTANCE.convert(dataSourceConfig));
    }

    @GetMapping("/list")
    @ApiOperation("获得数据源配置列表")
    @PreAuthorize("@pms.hasPermission('infra:data-source-config:query')")
    public R<List<DataSourceConfigRespVO>> getDataSourceConfigList() {
        List<DataSourceConfigDO> list = dataSourceConfigService.getDataSourceConfigList();
        return R.success(DataSourceConfigConvert.INSTANCE.convertList(list));
    }

}
