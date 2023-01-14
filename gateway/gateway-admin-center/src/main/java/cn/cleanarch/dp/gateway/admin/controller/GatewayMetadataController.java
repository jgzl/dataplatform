package cn.cleanarch.dp.gateway.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.cleanarch.dp.common.model.PageParam;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.poi.ExcelUtil;
import cn.cleanarch.dp.gateway.admin.convert.GatewayMetadataConvert;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayMetadataDO;
import cn.cleanarch.dp.gateway.admin.service.GatewayMetadataService;
import cn.cleanarch.dp.gateway.admin.vo.GatewayMetadataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

 /**
 * 网关管理-元数据管理
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
@Slf4j
@Api(tags = "网关管理-元数据管理对象功能接口")
@RestController
@RequestMapping("/gateway/metadata")
public class GatewayMetadataController{
    @Autowired
    private GatewayMetadataService gatewayMetadataService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("/{id}")
    public R<GatewayMetadataDO> getById(@PathVariable String id) {
        return R.success(gatewayMetadataService.getById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param gatewayMetadata 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public R<Page<GatewayMetadataDO>> paginQuery(GatewayMetadataDO gatewayMetadata, PageParam pageRequest) {
        //1.分页参数
        long current = pageRequest.getCurrent();
        long size = pageRequest.getSize();
        //2.分页查询
        Page<GatewayMetadataDO> pageResult = gatewayMetadataService.paginQuery(gatewayMetadata, current,size);
        return R.success(pageResult);
    }
    
    /** 
     * 新增数据
     *
     * @param gatewayMetadata 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public R<GatewayMetadataDO> save(@RequestBody GatewayMetadataDO gatewayMetadata) {
        gatewayMetadataService.save(gatewayMetadata);
        return R.success(gatewayMetadata);
    }
    
    /** 
     * 通过主键更新数据
     *
     * @param gatewayMetadata 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public R<GatewayMetadataDO> updateById(@RequestBody GatewayMetadataDO gatewayMetadata) {
        gatewayMetadataService.updateById(gatewayMetadata);
        return R.success(gatewayMetadata);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping("/{id}")
    public R<Boolean> deleteById(@PathVariable String id) {
        return R.success(gatewayMetadataService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList())));
    }

    /**
     * 导入excel
     */
    @SneakyThrows
    @ApiOperation("导入excel")
    @PostMapping("/import")
    public R<Void> importExcel(@RequestParam(value = "file") MultipartFile multipartFile) {
        ExcelUtil<GatewayMetadataVO> excelUtil = new ExcelUtil<>(GatewayMetadataVO.class);
        List<GatewayMetadataVO> voList = excelUtil.importExcel(multipartFile.getInputStream());
        List<GatewayMetadataDO> doList = GatewayMetadataConvert.INSTANCE.convertVO2DOList(voList);
        List<GatewayMetadataDO> dbList = gatewayMetadataService.list();
        Map<String, GatewayMetadataDO> dbPathMap = dbList.stream().collect(Collectors.toMap(GatewayMetadataDO::getPath, Function.identity()));
        Set<String> dbPathCollection = dbPathMap.keySet();
        List<GatewayMetadataDO> saveDoList = doList.stream().filter(domain->!dbPathCollection.contains(domain.getPath())).collect(Collectors.toList());
        gatewayMetadataService.saveBatch(saveDoList);
        List<GatewayMetadataDO> updateDoList = doList.stream().filter(domain->dbPathCollection.contains(domain.getPath())).collect(Collectors.toList());
        updateDoList.forEach(domain->domain.setId(dbPathMap.get(domain.getPath()).getId()));
        gatewayMetadataService.updateBatchById(updateDoList);
        return R.success();
    }

    /**
     * 导出excel
     */
    @SneakyThrows
    @ApiOperation("导出excel")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        List<GatewayMetadataDO> doList = gatewayMetadataService.list();
        ExcelUtil<GatewayMetadataVO> excelUtil = new ExcelUtil<>(GatewayMetadataVO.class);
        List<GatewayMetadataVO> voList = GatewayMetadataConvert.INSTANCE.convertDO2VOList(doList);
        excelUtil.exportExcel(response,voList,"网关元数据");
    }
}