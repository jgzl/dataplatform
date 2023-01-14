package cn.cleanarch.dp.gateway.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.dataobject.Route;
import cn.cleanarch.dp.common.gateway.ext.service.RouteService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.gateway.fish.service.DynamicRouteService;
import cn.cleanarch.dp.gateway.fish.service.LoadRouteService;
import cn.cleanarch.dp.gateway.fish.vo.GatewayRouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 动态添加路由
 * @Author jianglong
 * @Date 2020/05/11
 * @Version V1.0
 */
@RestController
@RequestMapping("/gateway/route")
public class RouteController {
    @Resource
    private DynamicRouteService dynamicRouteService;
    @Resource
    private LoadRouteService loadRouteService;
    @Resource
    private RouteService routeService;

    /**
     add-json:
     {
     "filters":[
         {
             "name":"StripPrefix",
             "args":{
             "_genkey_0":"1"
             }
         }
     ],
     "id":"hi_producer",
     "uri":"lb://service-hi",
     "order":1,
     "predicates":[
         {
             "name":"Path",
             "args":{
                "pattern":"/hi/producer/**"
             }
         }
     ]
     }
     */

    /**
     * 增加路由
     * @param gwdefinition
     * @return
     */
    @PostMapping("/add")
    public ApiResult add(@RequestBody GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = loadRouteService.assembleRouteDefinition(gwdefinition);
        this.dynamicRouteService.add(definition);
        return new ApiResult();
    }

    /**
     * 删除路由
     * @param id
     * @return
     */
    @DeleteMapping("/routes/{id}")
    public ApiResult delete(@PathVariable String id) {
        this.dynamicRouteService.delete(id);
        return new ApiResult();
    }

    /**
     * 更新路由
     * @param gwdefinition
     * @return
     */
    @PostMapping("/update")
    public ApiResult update(@RequestBody GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = loadRouteService.assembleRouteDefinition(gwdefinition);
        this.dynamicRouteService.update(definition);
        return new ApiResult();
    }

    /**
     * 从数据库加载指定路由
     * @return
     */
    @GetMapping(value = "/load")
    public ApiResult load(@RequestParam String id) {
        Assert.notNull(id, "路由ID不能为空");
        Route route = routeService.findById(id);
        RouteDefinition routeDefinition = loadRouteService.loadRouteDefinition(route);
        this.dynamicRouteService.add(routeDefinition);
        return new ApiResult();
    }

}
