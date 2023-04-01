package cn.cleanarch.dp.gateway.admin.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayGroovyScriptDO;
import cn.cleanarch.dp.common.gateway.ext.service.CustomNacosConfigService;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayGroovyScriptService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description 规则引擎动态脚本管理控制器
 * @Author JL
 * @Date 2022/2/22
 * @Version V1.0
 */
@Slf4j
@RestController
@RequestMapping("/gateway/groovyScript")
public class GatewayGroovyScriptController extends BaseRest {

    @Resource
    private GatewayGroovyScriptService gatewayGroovyScriptService;

    @Resource
    private CustomNacosConfigService customNacosConfigService;

    /**
     * 添加动态脚本
     * @param gatewayGroovyScriptDO
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ApiResult add(@RequestBody GatewayGroovyScriptDO gatewayGroovyScriptDO) throws Exception {
        Assert.notNull(gatewayGroovyScriptDO, "未获取到对象");
        gatewayGroovyScriptDO.setOrderNum(1);
        gatewayGroovyScriptDO.setStatus(Constants.NO);
        gatewayGroovyScriptDO.setCreateTime(new Date());
        this.validate(gatewayGroovyScriptDO);
        // 编译一下
        gatewayGroovyScriptService.instance(gatewayGroovyScriptDO);
        //查询最大orderNum
        int orderNum = gatewayGroovyScriptService.findMaxOrderNum(gatewayGroovyScriptDO.getRouteId(), gatewayGroovyScriptDO.getEvent());
        gatewayGroovyScriptDO.setOrderNum(orderNum + 1);
        gatewayGroovyScriptService.save(gatewayGroovyScriptDO);
        //将ID推送到nacos注册发现与配置中心
        customNacosConfigService.publishGroovyScriptNacosConfig(gatewayGroovyScriptDO.getId());
        return new ApiResult();
    }

    /**
     * 获取已注册网关路由列表
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult list(@RequestParam String routeId) {
        Assert.isTrue(StringUtils.isNotBlank(routeId), "未获取到对象网关路由ID");
        GatewayGroovyScriptDO gatewayGroovyScriptDO = new GatewayGroovyScriptDO();
        gatewayGroovyScriptDO.setRouteId(routeId);
        return new ApiResult(gatewayGroovyScriptService.list(gatewayGroovyScriptDO));
    }

    /**
     * 删除动态脚本
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult delete(@RequestParam Long id) {
        GatewayGroovyScriptDO gatewayGroovyScriptDO = getGroovyScript(id);
        gatewayGroovyScriptService.delete(gatewayGroovyScriptDO);
        if (Constants.YES.equals(gatewayGroovyScriptDO.getStatus())) {
            customNacosConfigService.publishGroovyScriptNacosConfig(id);
        }
        return new ApiResult();
    }

    /**
     * 更新动态脚本
     * @param gatewayGroovyScriptDO
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiResult update(@RequestBody GatewayGroovyScriptDO gatewayGroovyScriptDO) throws Exception {
        Assert.notNull(gatewayGroovyScriptDO, "未获取到对象");
        Long id = gatewayGroovyScriptDO.getId();
        Assert.notNull(id, "未获取到对象ID");
        Assert.isTrue(id>0, "ID值错误");
        gatewayGroovyScriptDO.setUpdateTime(new Date());
        this.validate(gatewayGroovyScriptDO);
        // 编译一下
        gatewayGroovyScriptService.instance(gatewayGroovyScriptDO);
        gatewayGroovyScriptService.update(gatewayGroovyScriptDO);
        if (Constants.YES.equals(gatewayGroovyScriptDO.getStatus())) {
            customNacosConfigService.publishGroovyScriptNacosConfig(id);
        }
        return new ApiResult();
    }

    /**
     * 设置状态为启用
     * @param id
     * @return
     */
    @RequestMapping(value = "/start", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult start(@RequestParam Long id) {
        GatewayGroovyScriptDO gatewayGroovyScriptDO = getGroovyScript(id);
        if (Constants.NO.equals(gatewayGroovyScriptDO.getStatus())) {
            gatewayGroovyScriptDO.setStatus(Constants.YES);
            gatewayGroovyScriptDO.setUpdateTime(new Date());
            gatewayGroovyScriptService.update(gatewayGroovyScriptDO);
            customNacosConfigService.publishGroovyScriptNacosConfig(id);
        }
        return new ApiResult();
    }

    /**
     * 设置状态为禁用
     * @param id
     * @return
     */
    @RequestMapping(value = "/stop", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult stop(@RequestParam Long id) {
        GatewayGroovyScriptDO gatewayGroovyScriptDO = getGroovyScript(id);
        if (Constants.YES.equals(gatewayGroovyScriptDO.getStatus())) {
            gatewayGroovyScriptDO.setStatus(Constants.NO);
            gatewayGroovyScriptDO.setUpdateTime(new Date());
            gatewayGroovyScriptService.update(gatewayGroovyScriptDO);
            customNacosConfigService.publishGroovyScriptNacosConfig(id);
        }
        return new ApiResult();
    }

    /**
     * 上移（重新排序）
     * @param id
     * @return
     */
    @RequestMapping(value = "/up", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult up(@RequestParam Long id) {
        GatewayGroovyScriptDO gatewayGroovyScriptDO = getGroovyScript(id);
        if (gatewayGroovyScriptService.upOrderNum(gatewayGroovyScriptDO)){
            customNacosConfigService.publishGroovyScriptNacosConfig(id);
        }
        return new ApiResult();
    }

    /**
     * 下移（重新排序）
     * @param id
     * @return
     */
    @RequestMapping(value = "/down", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult down(@RequestParam Long id) {
        GatewayGroovyScriptDO gatewayGroovyScriptDO = getGroovyScript(id);
        if (gatewayGroovyScriptService.downOrderNum(gatewayGroovyScriptDO)){
            customNacosConfigService.publishGroovyScriptNacosConfig(id);
        }
        return new ApiResult();
    }

    /**
     * 获取指定ID对应的动态脚本数据库记录
     * @param id
     * @return
     */
    private GatewayGroovyScriptDO getGroovyScript(Long id){
        Assert.notNull(id, "未获取到对象ID");
        Assert.isTrue(id>0, "ID值错误");
        GatewayGroovyScriptDO gatewayGroovyScriptDO = gatewayGroovyScriptService.findById(id);
        Assert.notNull(gatewayGroovyScriptDO, "未获取到对象");
        return gatewayGroovyScriptDO;
    }

}
