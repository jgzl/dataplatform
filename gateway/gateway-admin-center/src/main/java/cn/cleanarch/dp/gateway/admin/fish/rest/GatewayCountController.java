package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayCountReq;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayCountService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description
 * @Author jianglong
 * @Date 2020/07/07
 * @Version V1.0
 */
@RestController
@RequestMapping("/gateway/count")
public class GatewayCountController extends BaseRest {

    @Resource
    private GatewayCountService gatewayCountService;

    /**
     * 负载类型的请求（需要在缓存filed前加一个balanced前缀，用于区分普通路由ID）
     * @param gatewayCountReq
     * @return
     */
    @RequestMapping(value = "/balanced/request", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult balancedRequest(@RequestBody GatewayCountReq gatewayCountReq) {
        return gatewayCountService.count(gatewayCountReq, true);
    }

    /**
     * 请求
     * @param gatewayCountReq
     * @return
     */
    @RequestMapping(value = "/request", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult request(@RequestBody GatewayCountReq gatewayCountReq) {
        return gatewayCountService.count(gatewayCountReq, false);
    }

    /**
     * 查询路由集合统计结果
     * @param gatewayCountReq
     * @return
     */
    @RequestMapping(value = "/route/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult routePageList(@RequestBody GatewayCountReq gatewayCountReq) {
        Assert.notNull(gatewayCountReq, "未获取到对象");
        int currentPage = getCurrentPage(gatewayCountReq.getCurrentPage());
        int pageSize = getPageSize(gatewayCountReq.getPageSize());
        GatewayRouteDO gatewayRouteDO = new GatewayRouteDO();
        if (StringUtils.isNotBlank(gatewayCountReq.getName())) {
            gatewayRouteDO.setName(gatewayCountReq.getName());
        }
        if (StringUtils.isNotBlank(gatewayCountReq.getGroupCode())){
            gatewayRouteDO.setGroupCode(gatewayCountReq.getGroupCode());
        }
        return gatewayCountService.countRouteList(gatewayRouteDO, currentPage, pageSize);
    }

    /**
     * 统计按7天和按24小时维度计算的请求总量
     * @return
     */
    @RequestMapping(value = "/request/total", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult routeTotal() {
        return new ApiResult(gatewayCountService.countRequestTotal());
    }

    /**
     * 统计按7天和按24小时维度计算的请求总量
     * @return
     */
    @RequestMapping(value = "/request/app/total", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult routeAppTotal(@RequestParam(required=false) String id) {
        return new ApiResult(gatewayCountService.countRequestTotal(id));
    }

    /**
     * 流量
     * @param routeIds
     * @return
     */
    @RequestMapping(value = "/flux", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult flux(@RequestParam String [] routeIds ) {
        Assert.isTrue(routeIds != null, "未获取到对象ID");
        return new ApiResult();
    }

}
