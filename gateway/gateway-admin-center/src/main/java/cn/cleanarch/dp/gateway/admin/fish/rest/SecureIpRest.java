package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewaySecureIpDOReq;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewaySecureIpDO;
import cn.cleanarch.dp.common.gateway.ext.service.CustomNacosConfigService;
import cn.cleanarch.dp.common.gateway.ext.service.GatewaySecureIpService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description Ip管理控制器
 * @Author JL
 * @Date 2020/05/28
 * @Version V1.0
 */
@RestController
@RequestMapping("/gateway/ip")
public class SecureIpRest extends BaseRest {

    @Resource
    private GatewaySecureIpService gatewaySecureIpService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CustomNacosConfigService customNacosConfigService;

    /**
     * 添加IP
     * @param gatewaySecureIpDO
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ApiResult add(@RequestBody GatewaySecureIpDO gatewaySecureIpDO) {
        Assert.notNull(gatewaySecureIpDO, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(gatewaySecureIpDO.getIp()), "IP值不能为空");
        gatewaySecureIpDO.setCreateTime(new Date());
        this.validate(gatewaySecureIpDO);
        //验证注册服务是否重复
        GatewaySecureIpDO qGatewaySecureIpDO = new GatewaySecureIpDO();
        qGatewaySecureIpDO.setIp(gatewaySecureIpDO.getIp());
        long count = gatewaySecureIpService.count(qGatewaySecureIpDO);
        Assert.isTrue(count <= 0, "该IP已添加，请不要重复添加");
        gatewaySecureIpService.save(gatewaySecureIpDO);
        //this.setIpCacheVersion();
        customNacosConfigService.publishIpNacosConfig(gatewaySecureIpDO.getIp());
        return new ApiResult();
    }

    /**
     * 删除IP
     * @param ip
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult delete(@RequestParam String ip) {
        Assert.isTrue(StringUtils.isNotBlank(ip), "IP值不能为空");
        gatewaySecureIpService.deleteById(ip);
        //this.setIpCacheVersion();
        customNacosConfigService.publishIpNacosConfig(ip);
        return new ApiResult();
    }

    /**
     * 更新IP
     * @param gatewaySecureIpDO
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiResult update(@RequestBody GatewaySecureIpDO gatewaySecureIpDO) {
        Assert.notNull(gatewaySecureIpDO, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(gatewaySecureIpDO.getIp()), "IP值不能为空");
        gatewaySecureIpDO.setUpdateTime(new Date());
        this.validate(gatewaySecureIpDO);
        gatewaySecureIpService.update(gatewaySecureIpDO);
        //this.setIpCacheVersion();
        customNacosConfigService.publishIpNacosConfig(gatewaySecureIpDO.getIp());
        return new ApiResult();
    }

    /**
     * 查询IP
     * @param ip
     * @return
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult findById(@RequestParam String ip) {
        Assert.isTrue(StringUtils.isNotBlank(ip), "IP值不能为空");
        return new ApiResult(gatewaySecureIpService.findById(ip));
    }

    /**
     * 分页查询
     * @param secureIpReq
     * @return
     */
    @RequestMapping(value = "/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult pageList(@RequestBody GatewaySecureIpDOReq secureIpReq){
        Assert.notNull(secureIpReq, "未获取到对象");
        int currentPage = getCurrentPage(secureIpReq.getCurrentPage());
        int pageSize = getPageSize(secureIpReq.getPageSize());
        GatewaySecureIpDO gatewaySecureIpDO = new GatewaySecureIpDO();
        if (StringUtils.isNotBlank(secureIpReq.getIp())){
            gatewaySecureIpDO.setIp(secureIpReq.getIp());
        }
        if (StringUtils.isNotBlank(secureIpReq.getStatus())){
            gatewaySecureIpDO.setStatus(secureIpReq.getStatus());
        }
        return new ApiResult(gatewaySecureIpService.pageList(gatewaySecureIpDO,currentPage, pageSize));
    }

}
