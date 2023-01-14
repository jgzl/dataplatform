package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.vo.CountReq;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Route;
import cn.cleanarch.dp.common.gateway.ext.service.CountService;
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
public class CountRest extends BaseRest {

    @Resource
    private CountService countService;

    /**
     * 负载类型的请求（需要在缓存filed前加一个balanced前缀，用于区分普通路由ID）
     * @param countReq
     * @return
     */
    @RequestMapping(value = "/balanced/request", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult balancedRequest(@RequestBody CountReq countReq) {
        return countService.count(countReq, true);
    }

    /**
     * 请求
     * @param countReq
     * @return
     */
    @RequestMapping(value = "/request", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult request(@RequestBody CountReq countReq) {
        return countService.count(countReq, false);
    }

    /**
     * 查询路由集合统计结果
     * @param countReq
     * @return
     */
    @RequestMapping(value = "/route/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult routePageList(@RequestBody CountReq countReq) {
        Assert.notNull(countReq, "未获取到对象");
        int currentPage = getCurrentPage(countReq.getCurrentPage());
        int pageSize = getPageSize(countReq.getPageSize());
        Route route = new Route();
        if (StringUtils.isNotBlank(countReq.getName())) {
            route.setName(countReq.getName());
        }
        if (StringUtils.isNotBlank(countReq.getGroupCode())){
            route.setGroupCode(countReq.getGroupCode());
        }
        return countService.countRouteList(route, currentPage, pageSize);
    }

    /**
     * 统计按7天和按24小时维度计算的请求总量
     * @return
     */
    @RequestMapping(value = "/request/total", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult routeTotal() {
        return new ApiResult(countService.countRequestTotal());
    }

    /**
     * 统计按7天和按24小时维度计算的请求总量
     * @return
     */
    @RequestMapping(value = "/request/app/total", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult routeAppTotal(@RequestParam(required=false) String id) {
        return new ApiResult(countService.countRequestTotal(id));
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
