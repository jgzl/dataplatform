package cn.cleanarch.dp.gateway.admin.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.gateway.admin.service.GatewayRouteConfService;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayRouteConfDO;
import cn.cleanarch.dp.gateway.admin.vo.GatewayRouteConfVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网关管理-网关路由模块
 *
 * @author li7hai26@gmail.com
 * @date 2018-11-06 10:17:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/gateway/route")
public class GatewayRouteConfController {

    private final GatewayRouteConfService service;
    /**
     * 分页获取当前定义的信息
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('gateway:gateway-route:query')")
    public R<IPage<GatewayRouteConfDO>> pageListRoutes(Page page, GatewayRouteConfVO vo) {
        return R.success(service.page(page, new QueryWrapper<GatewayRouteConfDO>(vo)));
    }

    /**
     * 获取当前定义的路由信息
     *
     * @return
     */
    @GetMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-route:query')")
    public R<List<GatewayRouteConfDO>> listRoutes() {
        return R.success(service.list());
    }

    /**
     * 新增路由
     *
     * @param vo 路由定义
     * @return
     */
    @PostMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-route:create')")
    public R<Void> createRoutes(@RequestBody GatewayRouteConfDO vo) {
        service.saveOrUpdate(vo);
        return R.success();
    }

    /**
     * 修改路由
     *
     * @param vo 路由定义
     * @return
     */
    @PutMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-route:update')")
    public R<Void> updateRoutes(@RequestBody GatewayRouteConfDO vo) {
        service.saveOrUpdate(vo);
        return R.success();
    }

    /**
     * 删除路由
     *
     * @param routeId 路由id
     * @return
     */
    @DeleteMapping("/{routeId}")
    @PreAuthorize("@pms.hasPermission('gateway:gateway-route:delete')")
    public R<Void> deleteRoutes(@PathVariable String routeId) {
        service.deleteRoute(routeId);
        return R.success();
    }

}
