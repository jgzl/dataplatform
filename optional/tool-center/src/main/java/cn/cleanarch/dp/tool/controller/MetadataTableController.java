package cn.cleanarch.dp.tool.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.metadata.dto.MetadataTableDto;
import cn.cleanarch.dp.metadata.entity.MetadataTableEntity;
import cn.cleanarch.dp.metadata.query.MetadataTableQuery;
import cn.cleanarch.dp.metadata.validate.ValidationGroups;
import cn.cleanarch.dp.metadata.vo.MetadataTableVo;
import cn.cleanarch.dp.tool.mapstruct.MetadataTableMapper;
import cn.cleanarch.dp.tool.service.MetadataTableService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据库表信息表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Api(tags = {"数据库表信息表"})
@RestController
@RequestMapping("/tables")
public class MetadataTableController {

    @Autowired
    private MetadataTableService metadataTableService;

    @Autowired
    private MetadataTableMapper metadataTableMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public R getDataMetadataTableById(@PathVariable String id) {
        MetadataTableEntity metadataTableEntity = metadataTableService.getMetadataTableById(id);
        return R.success(metadataTableMapper.toVO(metadataTableEntity));
    }

    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping("/list")
    public R getDataMetadataTableList(MetadataTableQuery metadataTableQuery) {
//        QueryWrapper<MetadataTableEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like(StrUtil.isNotBlank(metadataTableQuery.getTableName()), "table_name", metadataTableQuery.getTableName());
//        queryWrapper.eq(StrUtil.isNotBlank(metadataTableQuery.getSourceId()), "source_id", metadataTableQuery.getSourceId());
//        List<MetadataTableEntity> list = metadataTableService.list(queryWrapper);
        List<MetadataTableEntity> list = metadataTableService.getDataMetadataTableList(metadataTableQuery);
        List<MetadataTableVo> collect = list.stream().map(metadataTableMapper::toVO).collect(Collectors.toList());
        return R.success(collect);
    }

    /**
     * 分页查询信息
     *
     * @param metadataTableQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataMetadataTableQuery", value = "查询实体dataMetadataTableQuery", required = true, dataTypeClass = MetadataTableQuery.class)
    })
    @GetMapping("/page")
    public R getDataMetadataTablePage(MetadataTableQuery metadataTableQuery) {
        QueryWrapper<MetadataTableEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(metadataTableQuery.getTableName()), "t.table_name", metadataTableQuery.getTableName());
        queryWrapper.eq(StrUtil.isNotBlank(metadataTableQuery.getSourceId()), "t.source_id", metadataTableQuery.getSourceId());
        IPage<MetadataTableEntity> page = metadataTableService.pageWithAuth(new Page<>(metadataTableQuery.getPageNum(), metadataTableQuery.getPageSize()), queryWrapper);
        return R.success(page);
    }

    /**
     * 添加
     * @param dataMetadataTable
     * @return
     */
    @ApiOperation(value = "添加信息", notes = "根据dataMetadataTable对象添加信息")
    @ApiImplicitParam(name = "dataMetadataTable", value = "详细实体dataMetadataTable", required = true, dataType = "DataMetadataTableDto")
    @PostMapping()
    public R saveDataMetadataTable(@RequestBody @Validated({ValidationGroups.Insert.class}) MetadataTableDto dataMetadataTable) {
        MetadataTableEntity metadataTableEntity = metadataTableService.saveMetadataTable(dataMetadataTable);
        return R.success(metadataTableMapper.toVO(metadataTableEntity));
    }

    /**
     * 修改
     * @param dataMetadataTable
     * @return
     */
    @ApiOperation(value = "修改信息", notes = "根据url的id来指定修改对象，并根据传过来的信息来修改详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "dataMetadataTable", value = "详细实体dataMetadataTable", required = true, dataType = "DataMetadataTableDto")
    })
    @PutMapping("/{id}")
    public R updateDataMetadataTable(@PathVariable String id, @RequestBody @Validated({ValidationGroups.Update.class}) MetadataTableDto dataMetadataTable) {
        MetadataTableEntity metadataTableEntity = metadataTableService.updateMetadataTable(dataMetadataTable);
        return R.success(metadataTableMapper.toVO(metadataTableEntity));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    public R deleteDataMetadataTableById(@PathVariable String id) {
        metadataTableService.deleteMetadataTableById(id);
        return R.success();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除角色", notes = "根据url的ids来批量删除对象")
    @ApiImplicitParam(name = "ids", value = "ID集合", required = true, dataType = "List", paramType = "path")
    @DeleteMapping("/batch/{ids}")
    public R deleteDataMetadataTableBatch(@PathVariable List<String> ids) {
        metadataTableService.deleteMetadataTableBatch(ids);
        return R.success();
    }
}
