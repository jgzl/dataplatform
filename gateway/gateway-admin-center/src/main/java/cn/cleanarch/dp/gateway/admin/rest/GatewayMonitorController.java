package cn.cleanarch.dp.gateway.admin.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayMonitorReq;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayMonitorDO;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayMonitorService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 接口监控
 * @Author JL
 * @Date 2021/04/14
 * @Version V1.0
 */
@RestController
@RequestMapping("/gateway/monitor")
public class GatewayMonitorController extends BaseRest {

    @Resource
    private GatewayMonitorService gatewayMonitorService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult list(@RequestBody GatewayMonitorReq gatewayMonitorReq){
        return new ApiResult(gatewayMonitorService.list(gatewayMonitorReq));
    }

    /**
     * 关闭本次告警，状态置为0正常
     * @param id
     * @return
     */
    @RequestMapping(value = "/close", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult close(@RequestParam String id){
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        GatewayMonitorDO gatewayMonitorDO = gatewayMonitorService.findById(id);
        Assert.notNull(gatewayMonitorDO, "未获取到对象");
        gatewayMonitorDO.setStatus(Constants.YES);
        gatewayMonitorService.update(gatewayMonitorDO);
        return new ApiResult();
    }

}
