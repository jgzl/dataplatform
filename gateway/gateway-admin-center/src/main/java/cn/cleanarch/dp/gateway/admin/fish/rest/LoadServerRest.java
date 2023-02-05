package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayLoadServerDOReq;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayLoadServerService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description 负载服务管理控制器
 * @Author JL
 * @Date 2020/06/28
 * @Version V1.0
 */
@RestController
@RequestMapping("/gateway/loadServer")
public class LoadServerRest extends BaseRest {

    @Resource
    private GatewayLoadServerService gatewayLoadServerService;

    /**
     * 查询当前负载网关已加配置路由服务
     * @param loadServerReq
     * @return
     */
    @RequestMapping(value = "/regList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult regList(@RequestBody GatewayLoadServerDOReq loadServerReq) {
        return list(loadServerReq, true);
    }

    /**
     * 查询当前负载网关未加配置路由服务
     * @param loadServerReq
     * @return
     */
    @RequestMapping(value = "/notRegPageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult notRegPageList(@RequestBody GatewayLoadServerDOReq loadServerReq) {
        return list(loadServerReq, false);
    }

    /**
     * 查询数据
     * @param loadServerReq
     * @param isReg
     * @return
     */
    private ApiResult list(GatewayLoadServerDOReq loadServerReq, boolean isReg){
        Assert.notNull(loadServerReq, "未获取到对象");
        if (isReg) {
            Assert.isTrue(StringUtils.isNotBlank(loadServerReq.getBalancedId()), "未获取到对象ID");
            return new ApiResult(gatewayLoadServerService.loadServerList(loadServerReq.getBalancedId()));
        }else {
            int currentPage = getCurrentPage(loadServerReq.getCurrentPage());
            int pageSize = getPageSize(loadServerReq.getPageSize());
            return new ApiResult(gatewayLoadServerService.notLoadServerPageList(currentPage, pageSize));
        }
    }

}
