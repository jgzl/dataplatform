package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayApiDocDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayApiDocService;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayRouteService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description API接口文档控制器
 * @Author JL
 * @Date 2020/11/24
 * @Version V1.0
 */
@RestController
@RequestMapping("/gateway/apiDoc")
public class GatewayApiDocController extends BaseRest {

    @Resource
    private GatewayRouteService gatewayRouteService;

    @Resource
    private GatewayApiDocService gatewayApiDocService;

    /**
     * 获取接口列表
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult list(){
        return new ApiResult(gatewayRouteService.list(new GatewayRouteDO()));
    }

    /**
     * 保存API文档
     * @param gatewayApiDocDO
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ApiResult save(@RequestBody GatewayApiDocDO gatewayApiDocDO){
        Assert.notNull(gatewayApiDocDO, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(gatewayApiDocDO.getId()), "未获取到对象ID");
        gatewayApiDocService.save(gatewayApiDocDO);
        return new ApiResult();
    }

    /**
     * 查询API文档
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult findById(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到请求ID");
        return new ApiResult(gatewayApiDocService.findById(id));
    }

}
