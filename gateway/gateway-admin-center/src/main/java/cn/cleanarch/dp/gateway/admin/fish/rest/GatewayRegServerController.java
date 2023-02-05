package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRegServerDO;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayRegServerDOReq;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayTokenReq;
import cn.cleanarch.dp.common.gateway.ext.service.CustomNacosConfigService;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayRegServerService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description 客户端注册服务控制器类
 * @Author JL
 * @Date 2020/05/16
 * @Version V1.0
 */
@RestController
@RequestMapping("/gateway/regServer")
public class GatewayRegServerController extends BaseRest {

    @Resource
    private GatewayRegServerService gatewayRegServerService;

    @Resource
    private CustomNacosConfigService customNacosConfigService;

    /**
     * 添加注册到网关路由的客户端服务
     * @param gatewayRegServerDO
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ApiResult add(@RequestBody GatewayRegServerDO gatewayRegServerDO) {
        Assert.notNull(gatewayRegServerDO, "未获取到对象");
        //默认禁止通行
        gatewayRegServerDO.setStatus(Constants.NO);
        gatewayRegServerDO.setCreateTime(new Date());
        this.validate(gatewayRegServerDO);
        //验证注册服务是否重复
        GatewayRegServerDO qServer = new GatewayRegServerDO();
        qServer.setClientId(gatewayRegServerDO.getClientId());
        qServer.setRouteId(gatewayRegServerDO.getRouteId());
        long count = gatewayRegServerService.count(qServer);
        Assert.isTrue(count <= 0, "客户端已注册该服务，请不要重复注册");
        //保存
        gatewayRegServerService.save(gatewayRegServerDO);
        //this.setClientCacheVersion();
        customNacosConfigService.publishRegServerNacosConfig(gatewayRegServerDO.getId());
        return new ApiResult();
    }

    /**
     * 删除注册到网关路由的客户端服务
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult delete(@RequestParam Long id) {
        Assert.notNull(id, "未获取到对象ID");
        Assert.isTrue(id>0, "ID值错误");
        gatewayRegServerService.deleteById(id);
        //this.setClientCacheVersion();
        customNacosConfigService.publishRegServerNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 更新注册到网关路由的客户端服务
     * @param gatewayRegServerDO
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiResult update(@RequestBody GatewayRegServerDO gatewayRegServerDO) {
        Assert.notNull(gatewayRegServerDO, "未获取到对象");
        gatewayRegServerDO.setUpdateTime(new Date());
        this.validate(gatewayRegServerDO);
        gatewayRegServerService.update(gatewayRegServerDO);
        //this.setClientCacheVersion();
        customNacosConfigService.publishRegServerNacosConfig(gatewayRegServerDO.getId());
        return new ApiResult();
    }

    /**
     * 获取注册网关路由客户端服务
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult findById(@RequestParam Long id) {
        Assert.notNull(id, "未获取到对象ID");
        Assert.isTrue(id>0, "ID值错误");
        return new ApiResult(gatewayRegServerService.findById(id));
    }

    /**
     * 获取网关路由服务列表（分页）
     * @param regServerReq
     * @return
     */
    @RequestMapping(value = "/serverPageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult serverPageList(@RequestBody GatewayRegServerDOReq regServerReq) {
        Assert.notNull(regServerReq, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(regServerReq.getClientId()), "未获取到对象查询ID");
        GatewayRegServerDO gatewayRegServerDO = new GatewayRegServerDO();
        gatewayRegServerDO.setClientId(regServerReq.getClientId());
        int currentPage = getCurrentPage(regServerReq.getCurrentPage());
        int pageSize = getPageSize(regServerReq.getPageSize());
        return new ApiResult(gatewayRegServerService.serverPageList(gatewayRegServerDO, currentPage, pageSize));
    }

    /**
     * 获取客户端列表（分页）
     * @param regServerReq
     * @return
     */
    @RequestMapping(value = "/clientPageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult clientPageList(@RequestBody GatewayRegServerDOReq regServerReq) {
        Assert.notNull(regServerReq, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(regServerReq.getRouteId()), "未获取到对象查询ID");
        GatewayRegServerDO gatewayRegServerDO = new GatewayRegServerDO();
        gatewayRegServerDO.setRouteId(regServerReq.getRouteId());
        int currentPage = getCurrentPage(regServerReq.getCurrentPage());
        int pageSize = getPageSize(regServerReq.getPageSize());
        return new ApiResult(gatewayRegServerService.clientPageList(gatewayRegServerDO, currentPage, pageSize));
    }

    /**
     * 获取已注册网关路由列表
     * @param regServerReq
     * @return
     */
    @RequestMapping(value = "/regClientList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult regClientList(@RequestBody GatewayRegServerDOReq regServerReq) {
        Assert.notNull(regServerReq, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(regServerReq.getRouteId()), "未获取到对象查询ID");
        GatewayRegServerDO gatewayRegServerDO = new GatewayRegServerDO();
        gatewayRegServerDO.setRouteId(regServerReq.getRouteId());
        return new ApiResult(gatewayRegServerService.regClientList(gatewayRegServerDO));
    }

    /**
     * 启用注册网关路由下的客户端
     * @param id
     * @return
     */
    @RequestMapping(value = "/start", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult start(@RequestParam Long id) {
        Assert.notNull(id, "未获取到对象ID");
        Assert.isTrue(id>0, "ID值错误");
        GatewayRegServerDO dbGatewayRegServerDO = gatewayRegServerService.findById(id);
        dbGatewayRegServerDO.setStatus(Constants.YES);
        dbGatewayRegServerDO.setUpdateTime(new Date());
        gatewayRegServerService.update(dbGatewayRegServerDO);
        //this.setClientCacheVersion();
        customNacosConfigService.publishRegServerNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 禁用注册网关路由下的客户端
     * @param id
     * @return
     */
    @RequestMapping(value = "/stop", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult stop(@RequestParam Long id) {
        Assert.notNull(id, "未获取到对象ID");
        Assert.isTrue(id>0, "ID值错误");
        GatewayRegServerDO dbGatewayRegServerDO = gatewayRegServerService.findById(id);
        dbGatewayRegServerDO.setStatus(Constants.NO);
        dbGatewayRegServerDO.setUpdateTime(new Date());
        gatewayRegServerService.update(dbGatewayRegServerDO);
        //this.setClientCacheVersion();
        customNacosConfigService.publishRegServerNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 禁用当前客户端，关闭其注册的所有网关路由服务端访问状态
     * @param clientId
     * @return
     */
    @RequestMapping(value = "/stopClientAllRoute", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult stopClientAllRoute(@RequestParam String clientId) {
        Assert.isTrue(StringUtils.isNotBlank(clientId), "未获取到对象ID");
        gatewayRegServerService.stopClientAllRoute(clientId);
        customNacosConfigService.publishClientNacosConfig(clientId);
        return new ApiResult();
    }

    /**
     * 启用当前客户端，激活其注册的所有网关路由服务端访问状态
     * @param clientId
     * @return
     */
    @RequestMapping(value = "/startClientAllRoute", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult startClientAllRoute(@RequestParam String clientId) {
        Assert.isTrue(StringUtils.isNotBlank(clientId), "未获取到对象ID");
        gatewayRegServerService.startClientAllRoute(clientId);
        customNacosConfigService.publishClientNacosConfig(clientId);
        return new ApiResult();
    }

    /**
     * 关闭网关路由下所有注册客户端
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/stopRouteAllClient", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult stopRouteAllClient(@RequestParam String routeId) {
        Assert.isTrue(StringUtils.isNotBlank(routeId), "未获取到对象ID");
        gatewayRegServerService.stopRouteAllClient(routeId);
        customNacosConfigService.publishRouteNacosConfig(routeId);
        return new ApiResult();
    }

    /**
     * 启用网关路由下所有注册客户端
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/startRouteAllClient", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult startRouteAllClient(@RequestParam String routeId) {
        Assert.isTrue(StringUtils.isNotBlank(routeId), "未获取到对象ID");
        gatewayRegServerService.startRouteAllClient(routeId);
        customNacosConfigService.publishRouteNacosConfig(routeId);
        return new ApiResult();
    }

    /**
     * 获取未注册到的服务列表（分页）
     * @param regServerReq
     * @return
     */
    @RequestMapping(value = "/notRegServerPageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult notRegServerPageList(@RequestBody GatewayRegServerDOReq regServerReq) {
        Assert.notNull(regServerReq, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(regServerReq.getClientId()), "未获取到客户端ID");
        int currentPage = getCurrentPage(regServerReq.getCurrentPage());
        int pageSize = getPageSize(regServerReq.getPageSize());
        return new ApiResult(gatewayRegServerService.notRegServerPageList(regServerReq, currentPage, pageSize));
    }

    /**
     * 获取未注册的客户端列表（分页）
     * @param regServerReq
     * @return
     */
    @RequestMapping(value = "/notRegClientPageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult notRegClientPageList(@RequestBody GatewayRegServerDOReq regServerReq) {
        Assert.notNull(regServerReq, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(regServerReq.getRouteId()), "未获取路由服务ID");
        int currentPage = getCurrentPage(regServerReq.getCurrentPage());
        int pageSize = getPageSize(regServerReq.getPageSize());
        return new ApiResult(gatewayRegServerService.notRegClientPageList(regServerReq, currentPage, pageSize));
    }

    /**
     * 创建Token
     * @param gatewayTokenReq
     * @return
     */
    @RequestMapping(value = "/createToken", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult createToken(@RequestBody GatewayTokenReq gatewayTokenReq){
        Assert.notNull(gatewayTokenReq, "未获取到对象");
        Assert.notNull(gatewayTokenReq.getRegServerId(), "未获取到对象ID");
        Assert.isTrue(gatewayTokenReq.getRegServerId() > 0, "ID值错误");
        GatewayRegServerDO gatewayRegServerDO = gatewayRegServerService.findById(gatewayTokenReq.getRegServerId());
        Assert.notNull(gatewayRegServerDO, "未查询到对象");
        String sub = String.format("%s,%s,%d", gatewayRegServerDO.getRouteId(), gatewayRegServerDO.getClientId(), System.currentTimeMillis());
        Date tokenEffectiveTime = gatewayTokenReq.getTokenEffectiveTime();
        Assert.notNull(tokenEffectiveTime, "未获取Token有效过期时间");
        String secretKey = gatewayTokenReq.getSecretKey();
        if (StringUtils.isBlank(secretKey)){
            secretKey = gatewayRegServerDO.getClientId();
        }
        //创建Token
        String jwtToken = JwtTokenUtils.createToken(sub, tokenEffectiveTime, secretKey);
        //每次加密成功，更新数据库已有Token
        gatewayRegServerDO.setToken(jwtToken);
        gatewayRegServerDO.setSecretKey(secretKey);
        gatewayRegServerDO.setTokenEffectiveTime(tokenEffectiveTime);
        gatewayRegServerService.update(gatewayRegServerDO);
        //返回最新Token
        return new ApiResult(Constants.SUCCESS, jwtToken);
    }

    /**
     *清除Token
     * @param gatewayTokenReq
     * @return
     */
    @RequestMapping(value = "/removeToken", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult removeToken(@RequestBody GatewayTokenReq gatewayTokenReq){
        Assert.notNull(gatewayTokenReq, "未获取到对象");
        Assert.notNull(gatewayTokenReq.getRegServerId(), "未获取到对象ID");
        Assert.isTrue(gatewayTokenReq.getRegServerId() > 0, "ID值错误");
        GatewayRegServerDO gatewayRegServerDO = gatewayRegServerService.findById(gatewayTokenReq.getRegServerId());
        Assert.notNull(gatewayRegServerDO, "未查询到对象");
        //清除已有Token
        gatewayRegServerDO.setTokenEffectiveTime(null);
        gatewayRegServerDO.setToken(null);
        gatewayRegServerDO.setSecretKey(null);
        gatewayRegServerService.update(gatewayRegServerDO);
        return new ApiResult();
    }

}
