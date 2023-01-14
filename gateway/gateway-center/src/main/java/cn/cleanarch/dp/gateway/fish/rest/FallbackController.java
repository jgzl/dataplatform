package cn.cleanarch.dp.gateway.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.dataobject.Route;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.gateway.fish.cache.RouteCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 触发熔断机制响应控制器
 * @Author jianglong
 * @Date 2020/05/26
 * @Version V1.0
 */
@Slf4j
@RestController
@ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
public class FallbackController {

    /**
     * 触发熔断机制的回调方法
     * @return
     */
    @RequestMapping(value = "/fallback", method = {RequestMethod.GET,RequestMethod.POST})
    public ApiResult fallback(@RequestParam(required = false) String routeId) {
        log.error("触发熔断机制的回调方法:fallback,routeId={}", routeId);
        return new ApiResult(Constants.FAILED,"提示：服务响应超时，触发熔断机制，请联系运维人员处理。此消息由网关服务返回！",null);
    }

    /**
     * 触发自定义熔断机制的回调方法
     * @return
     */
    @RequestMapping(value = "/fallback/custom", method = {RequestMethod.GET,RequestMethod.POST})
    public ApiResult fallbackCustom(@RequestParam String routeId) {
        log.error("触发自定义熔断机制的回调方法:fallback,routeId={}", routeId);
        Route route = (Route) RouteCache.get(routeId);
        if (route != null){
            return new ApiResult(Constants.FAILED,"提示：" + route.getFallbackMsg(),null);
        }
        return new ApiResult(Constants.FAILED,"提示：服务响应超时，触发自定义熔断机制，请联系运维人员处理。此消息由网关服务返回！",null);
    }

}
