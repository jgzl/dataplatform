package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayMonitorDO;
import cn.cleanarch.dp.common.gateway.ext.service.CustomNacosConfigService;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayMonitorService;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayRouteService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
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
public class GatewayRouteController extends BaseRest {

    @Resource
    private GatewayRouteService gatewayRouteService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private GatewayMonitorService gatewayMonitorService;

    @Resource
    private CustomNacosConfigService customNacosConfigService;

    /**
     * 添加网关路由
     * @param gatewayRouteReq
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ApiResult add(@RequestBody GatewayRouteReq gatewayRouteReq){
        Assert.notNull(gatewayRouteReq, "未获取到对象");
        GatewayRouteDO gatewayRouteDO = toRoute(gatewayRouteReq);
        gatewayRouteDO.setCreateTime(new Date());
        this.validate(gatewayRouteDO);
        GatewayRouteDO dbGatewayRouteDO = new GatewayRouteDO();
        dbGatewayRouteDO.setId(gatewayRouteDO.getId());
        long count = gatewayRouteService.count(dbGatewayRouteDO);
        Assert.isTrue(count <= 0, "RouteId已存在，不能重复");
        return this.save(gatewayRouteDO, toMonitor(gatewayRouteReq), true);
    }

    /**
     * 删除网关路由
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult delete(@RequestParam String id){
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        gatewayRouteService.delete(id);
        //this.setRouteCacheVersion();
        customNacosConfigService.publishRouteNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 更新网关路由
     * @param gatewayRouteReq
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiResult update(@RequestBody GatewayRouteReq gatewayRouteReq){
        Assert.notNull(gatewayRouteReq, "未获取到对象");
        GatewayRouteDO gatewayRouteDO = toRoute(gatewayRouteReq);
        this.validate(gatewayRouteDO);
        Assert.isTrue(StringUtils.isNotBlank(gatewayRouteDO.getId()), "未获取到对象ID");
        return this.save(gatewayRouteDO, toMonitor(gatewayRouteReq), false);
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult findById(@RequestParam String id){
        Assert.notNull(id, "未获取到对象ID");
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        return new ApiResult(gatewayRouteService.findById(id));
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult list(@RequestBody GatewayRouteReq gatewayRouteReq){
        Assert.notNull(gatewayRouteReq, "未获取到对象");
        return new ApiResult(gatewayRouteService.list(toRoute(gatewayRouteReq)));
    }

    @RequestMapping(value = "/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult pageList(@RequestBody GatewayRouteReq gatewayRouteReq){
        Assert.notNull(gatewayRouteReq, "未获取到对象");
        int currentPage = getCurrentPage(gatewayRouteReq.getCurrentPage());
        int pageSize = getPageSize(gatewayRouteReq.getPageSize());
        GatewayRouteDO gatewayRouteDO = toRoute(gatewayRouteReq);
        if (StringUtils.isBlank(gatewayRouteDO.getName())){
            gatewayRouteDO.setName(null);
        }
        if (StringUtils.isBlank(gatewayRouteDO.getStatus())){
            gatewayRouteDO.setStatus(null);
        }
        return new ApiResult(gatewayRouteService.pageList(gatewayRouteDO,currentPage, pageSize));
    }

    /**
     * 启用网关路由服务
     * @param id
     * @return
     */
    @RequestMapping(value = "/start", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult start(@RequestParam String id){
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        GatewayRouteDO dbGatewayRouteDO = gatewayRouteService.findById(id);
        if (!Constants.YES.equals(dbGatewayRouteDO.getStatus())) {
            dbGatewayRouteDO.setStatus(Constants.YES);
            gatewayRouteService.update(dbGatewayRouteDO);
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
        GatewayRouteDO dbGatewayRouteDO = gatewayRouteService.findById(id);
        if (!Constants.NO.equals(dbGatewayRouteDO.getStatus())) {
            dbGatewayRouteDO.setStatus(Constants.NO);
            gatewayRouteService.update(dbGatewayRouteDO);
            //this.setRouteCacheVersion();
            customNacosConfigService.publishRouteNacosConfig(id);
        }
        return new ApiResult();
    }

    /**
     * 保存网关路由服务
     * @param gatewayRouteDO
     * @param gatewayMonitorDO
     * @param isNews
     * @return
     */
    private ApiResult save(GatewayRouteDO gatewayRouteDO, GatewayMonitorDO gatewayMonitorDO, boolean isNews){
        gatewayRouteDO.setUpdateTime(new Date());
        gatewayRouteService.save(gatewayRouteDO);
        //this.setRouteCacheVersion();
        customNacosConfigService.publishRouteNacosConfig(gatewayRouteDO.getId());
        //保存监控配置
        if (gatewayMonitorDO != null) {
            gatewayMonitorDO.setId(gatewayRouteDO.getId());
            gatewayMonitorDO.setUpdateTime(new Date());
            this.validate(gatewayMonitorDO);
            gatewayMonitorService.save(gatewayMonitorDO);
        } else {
            if (!isNews) {
                GatewayMonitorDO dbGatewayMonitorDO = gatewayMonitorService.findById(gatewayRouteDO.getId());
                //修改时，如果前端取消选中，并且数据库中又存在记录，则需要置为禁用状态
                if (dbGatewayMonitorDO != null){
                    dbGatewayMonitorDO.setStatus(Constants.NO);
                    dbGatewayMonitorDO.setUpdateTime(new Date());
                    gatewayMonitorService.update(dbGatewayMonitorDO);
                }
            }
        }
        return new ApiResult();
    }

    /**
     * 将请求对象转换为数据库实体对象
     * @param gatewayRouteReq  前端对象
     * @return Route
     */
    private GatewayRouteDO toRoute(GatewayRouteReq gatewayRouteReq){
        GatewayRouteDO gatewayRouteDO = new GatewayRouteDO();
        GatewayRouteDOFormBean form = gatewayRouteReq.getForm();
        if (form == null){
            return gatewayRouteDO;
        }
        BeanUtils.copyProperties(form, gatewayRouteDO);
        GatewayRouteFilterBean filter = gatewayRouteReq.getFilter();
        GatewayRouteHystrixBean hystrix = gatewayRouteReq.getHystrix();
        GatewayRouteLimiterBean limiter = gatewayRouteReq.getLimiter();
        GatewayRouteAccessBean access = gatewayRouteReq.getAccess();
        //添加过滤器
        if (filter != null) {
            List<String> routeFilterList = new ArrayList<>();
            if (filter.getIdChecked()) {
                routeFilterList.add(GatewayRouteConstants.ID);
            }
            if (filter.getIpChecked()) {
                routeFilterList.add(GatewayRouteConstants.IP);
            }
            if (filter.getTokenChecked()) {
                routeFilterList.add(GatewayRouteConstants.TOKEN);
            }
            gatewayRouteDO.setFilterGatewaName(StringUtils.join(routeFilterList.toArray(), Constants.SEPARATOR_SIGN));
        }

        //添加熔断器
        if (hystrix != null) {
            if (hystrix.getDefaultChecked()) {
                gatewayRouteDO.setFilterHystrixName(GatewayRouteConstants.Hystrix.DEFAULT);
            } else if (hystrix.getCustomChecked()) {
                gatewayRouteDO.setFilterHystrixName(GatewayRouteConstants.Hystrix.CUSTOM);
            }
        }
        //添加限流器
        if (limiter != null) {
            gatewayRouteDO.setFilterRateLimiterName(null);
            if (limiter.getIdChecked()) {
                gatewayRouteDO.setFilterRateLimiterName(GatewayRouteConstants.REQUEST_ID);
            }else if (limiter.getIpChecked()) {
                gatewayRouteDO.setFilterRateLimiterName(GatewayRouteConstants.IP);
            }else if (limiter.getUriChecked()) {
                gatewayRouteDO.setFilterRateLimiterName(GatewayRouteConstants.URI);
            }
        }
        //添加鉴权器
        if (access != null) {
            List<String> routeAccessList = new ArrayList<>();
            if (access.getHeaderChecked()) {
                routeAccessList.add(GatewayRouteConstants.Access.HEADER);
            }
            if (access.getIpChecked()) {
                routeAccessList.add(GatewayRouteConstants.Access.IP);
            }
            if (access.getParameterChecked()) {
                routeAccessList.add(GatewayRouteConstants.Access.PARAMETER);
            }
            if (access.getTimeChecked()) {
                routeAccessList.add(GatewayRouteConstants.Access.TIME);
            }
            if (access.getCookieChecked()) {
                routeAccessList.add(GatewayRouteConstants.Access.COOKIE);
            }
            gatewayRouteDO.setFilterAuthorizeName(StringUtils.join(routeAccessList.toArray(), Constants.SEPARATOR_SIGN));
        }
        return gatewayRouteDO;
    }

    /**
     * 获取监控配置
     * @param gatewayRouteReq
     * @return
     */
    private GatewayMonitorDO toMonitor(GatewayRouteReq gatewayRouteReq){
        GatewayMonitorBean bean = gatewayRouteReq.getMonitor();
        if (bean != null){
            // checked为true，则表示启用监控配置
            if (bean.getChecked()){
                GatewayRouteDOFormBean form = gatewayRouteReq.getForm();
                GatewayMonitorDO gatewayMonitorDO = new GatewayMonitorDO();
                BeanUtils.copyProperties(form.getGatewayMonitorDO(), gatewayMonitorDO);
                gatewayMonitorDO.setStatus(Constants.YES);
                return gatewayMonitorDO;
            }
        }
        return null;
    }

}
