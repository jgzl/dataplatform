package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Monitor;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Route;
import cn.cleanarch.dp.common.gateway.ext.service.CustomNacosConfigService;
import cn.cleanarch.dp.common.gateway.ext.service.MonitorService;
import cn.cleanarch.dp.common.gateway.ext.service.RouteService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.RouteConstants;
import cn.cleanarch.dp.common.gateway.ext.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 路由管理
 * @Author JL
 * @Date 2020/05/14
 * @Version V1.0
 */
@Slf4j
@RestController
@RequestMapping("/gateway/route")
public class RouteRest extends BaseRest {

    @Resource
    private RouteService routeService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MonitorService monitorService;

    @Resource
    private CustomNacosConfigService customNacosConfigService;

    /**
     * 添加网关路由
     * @param routeReq
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ApiResult add(@RequestBody RouteReq routeReq){
        Assert.notNull(routeReq, "未获取到对象");
        Route route = toRoute(routeReq);
        route.setCreateTime(new Date());
        this.validate(route);
        Route dbRoute = new Route();
        dbRoute.setId(route.getId());
        long count = routeService.count(dbRoute);
        Assert.isTrue(count <= 0, "RouteId已存在，不能重复");
        return this.save(route, toMonitor(routeReq), true);
    }

    /**
     * 删除网关路由
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult delete(@RequestParam String id){
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        routeService.delete(id);
        //this.setRouteCacheVersion();
        customNacosConfigService.publishRouteNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 更新网关路由
     * @param routeReq
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiResult update(@RequestBody RouteReq routeReq){
        Assert.notNull(routeReq, "未获取到对象");
        Route route = toRoute(routeReq);
        this.validate(route);
        Assert.isTrue(StringUtils.isNotBlank(route.getId()), "未获取到对象ID");
        return this.save(route, toMonitor(routeReq), false);
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult findById(@RequestParam String id){
        Assert.notNull(id, "未获取到对象ID");
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        return new ApiResult(routeService.findById(id));
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult list(@RequestBody RouteReq routeReq){
        Assert.notNull(routeReq, "未获取到对象");
        return new ApiResult(routeService.list(toRoute(routeReq)));
    }

    @RequestMapping(value = "/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult pageList(@RequestBody RouteReq routeReq){
        Assert.notNull(routeReq, "未获取到对象");
        int currentPage = getCurrentPage(routeReq.getCurrentPage());
        int pageSize = getPageSize(routeReq.getPageSize());
        Route route = toRoute(routeReq);
        if (StringUtils.isBlank(route.getName())){
            route.setName(null);
        }
        if (StringUtils.isBlank(route.getStatus())){
            route.setStatus(null);
        }
        return new ApiResult(routeService.pageList(route,currentPage, pageSize));
    }

    /**
     * 启用网关路由服务
     * @param id
     * @return
     */
    @RequestMapping(value = "/start", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult start(@RequestParam String id){
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        Route dbRoute = routeService.findById(id);
        if (!Constants.YES.equals(dbRoute.getStatus())) {
            dbRoute.setStatus(Constants.YES);
            routeService.update(dbRoute);
        }
        //this.setRouteCacheVersion();
        //可以通过反复启用，刷新路由，防止发布失败或配置变更未生效
        customNacosConfigService.publishRouteNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 停止网关路由服务
     * @param id
     * @return
     */
    @RequestMapping(value = "/stop", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult stop(@RequestParam String id){
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        Route dbRoute = routeService.findById(id);
        if (!Constants.NO.equals(dbRoute.getStatus())) {
            dbRoute.setStatus(Constants.NO);
            routeService.update(dbRoute);
            //this.setRouteCacheVersion();
            customNacosConfigService.publishRouteNacosConfig(id);
        }
        return new ApiResult();
    }

    /**
     * 保存网关路由服务
     * @param route
     * @param monitor
     * @param isNews
     * @return
     */
    private ApiResult save(Route route, Monitor monitor, boolean isNews){
        route.setUpdateTime(new Date());
        routeService.save(route);
        //this.setRouteCacheVersion();
        customNacosConfigService.publishRouteNacosConfig(route.getId());
        //保存监控配置
        if (monitor != null) {
            monitor.setId(route.getId());
            monitor.setUpdateTime(new Date());
            this.validate(monitor);
            monitorService.save(monitor);
        } else {
            if (!isNews) {
                Monitor dbMonitor = monitorService.findById(route.getId());
                //修改时，如果前端取消选中，并且数据库中又存在记录，则需要置为禁用状态
                if (dbMonitor != null){
                    dbMonitor.setStatus(Constants.NO);
                    dbMonitor.setUpdateTime(new Date());
                    monitorService.update(dbMonitor);
                }
            }
        }
        return new ApiResult();
    }

    /**
     * 将请求对象转换为数据库实体对象
     * @param routeReq  前端对象
     * @return Route
     */
    private Route toRoute(RouteReq routeReq){
        Route route = new Route();
        RouteFormBean form = routeReq.getForm();
        if (form == null){
            return route;
        }
        BeanUtils.copyProperties(form, route);
        RouteFilterBean filter = routeReq.getFilter();
        RouteHystrixBean hystrix = routeReq.getHystrix();
        RouteLimiterBean limiter = routeReq.getLimiter();
        RouteAccessBean access = routeReq.getAccess();
        //添加过滤器
        if (filter != null) {
            List<String> routeFilterList = new ArrayList<>();
            if (filter.getIdChecked()) {
                routeFilterList.add(RouteConstants.ID);
            }
            if (filter.getIpChecked()) {
                routeFilterList.add(RouteConstants.IP);
            }
            if (filter.getTokenChecked()) {
                routeFilterList.add(RouteConstants.TOKEN);
            }
            route.setFilterGatewaName(StringUtils.join(routeFilterList.toArray(), Constants.SEPARATOR_SIGN));
        }

        //添加熔断器
        if (hystrix != null) {
            if (hystrix.getDefaultChecked()) {
                route.setFilterHystrixName(RouteConstants.Hystrix.DEFAULT);
            } else if (hystrix.getCustomChecked()) {
                route.setFilterHystrixName(RouteConstants.Hystrix.CUSTOM);
            }
        }
        //添加限流器
        if (limiter != null) {
            route.setFilterRateLimiterName(null);
            if (limiter.getIdChecked()) {
                route.setFilterRateLimiterName(RouteConstants.REQUEST_ID);
            }else if (limiter.getIpChecked()) {
                route.setFilterRateLimiterName(RouteConstants.IP);
            }else if (limiter.getUriChecked()) {
                route.setFilterRateLimiterName(RouteConstants.URI);
            }
        }
        //添加鉴权器
        if (access != null) {
            List<String> routeAccessList = new ArrayList<>();
            if (access.getHeaderChecked()) {
                routeAccessList.add(RouteConstants.Access.HEADER);
            }
            if (access.getIpChecked()) {
                routeAccessList.add(RouteConstants.Access.IP);
            }
            if (access.getParameterChecked()) {
                routeAccessList.add(RouteConstants.Access.PARAMETER);
            }
            if (access.getTimeChecked()) {
                routeAccessList.add(RouteConstants.Access.TIME);
            }
            if (access.getCookieChecked()) {
                routeAccessList.add(RouteConstants.Access.COOKIE);
            }
            route.setFilterAuthorizeName(StringUtils.join(routeAccessList.toArray(), Constants.SEPARATOR_SIGN));
        }
        return route;
    }

    /**
     * 获取监控配置
     * @param routeReq
     * @return
     */
    private Monitor toMonitor(RouteReq routeReq){
        MonitorBean bean = routeReq.getMonitor();
        if (bean != null){
            // checked为true，则表示启用监控配置
            if (bean.getChecked()){
                RouteFormBean form = routeReq.getForm();
                Monitor monitor = new Monitor();
                BeanUtils.copyProperties(form.getMonitor(), monitor);
                monitor.setStatus(Constants.YES);
                return monitor;
            }
        }
        return null;
    }

}
