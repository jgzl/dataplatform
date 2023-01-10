package cn.cleanarch.dp.gateway.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.model.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.cleanarch.dp.gateway.dataobject.GatewayApplicationDO;
import cn.cleanarch.dp.gateway.admin.service.GatewayApplicationService;

import java.util.Arrays;
import java.util.stream.Collectors;

 /**
 * 网关管理-应用服务
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
@Api(tags = "网关管理-应用服务表对象功能接口")
@RestController
@RequestMapping("/gateway/application")
public class GatewayApplicationController{
    @Autowired
    private GatewayApplicationService gatewayApplicationService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("/{id}")
    public R<GatewayApplicationDO> getById(@PathVariable String id) {
        return R.success(gatewayApplicationService.getById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param gatewayApplication 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public R<Page<GatewayApplicationDO>> paginQuery(GatewayApplicationDO gatewayApplication, PageParam pageRequest) {
        //1.分页参数
        long current = pageRequest.getCurrent();
        long size = pageRequest.getSize();
        //2.分页查询
        Page<GatewayApplicationDO> pageResult = gatewayApplicationService.paginQuery(gatewayApplication, current,size);
        return R.success(pageResult);
    }
    
    /** 
     * 新增数据
     *
     * @param gatewayApplication 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public R<GatewayApplicationDO> save(@RequestBody GatewayApplicationDO gatewayApplication) {
        gatewayApplicationService.save(gatewayApplication);
        return R.success(gatewayApplication);
    }
    
    /** 
     * 通过主键更新数据
     *
     * @param gatewayApplication 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public R<GatewayApplicationDO> updateById(@RequestBody GatewayApplicationDO gatewayApplication) {
        gatewayApplicationService.updateById(gatewayApplication);
        return R.success(gatewayApplication);
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
        return R.success(gatewayApplicationService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList())));
    }
}