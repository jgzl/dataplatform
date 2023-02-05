package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayClientDO;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayClientDOReq;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayClientService;
import cn.cleanarch.dp.common.gateway.ext.service.CustomNacosConfigService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description 客户端管理控制器类
 * @Author jianglong
 * @Date 2020/05/16
 * @Version V1.0
 */
@RestController
@RequestMapping("/gateway/client")
public class ClientRest extends BaseRest {

    @Resource
    private GatewayClientService gatewayClientService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CustomNacosConfigService customNacosConfigService;

    /**
     * 添加客户端
     * @param gatewayClientDO
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ApiResult add(@RequestBody GatewayClientDO gatewayClientDO) {
        Assert.notNull(gatewayClientDO, "未获取到对象");
        gatewayClientDO.setId(UUIDUtils.getUUIDString());
        gatewayClientDO.setCreateTime(new Date());
        this.validate(gatewayClientDO);
        //验证名称是否重复
        GatewayClientDO qClinet = new GatewayClientDO();
        qClinet.setName(gatewayClientDO.getName());
        long count = gatewayClientService.count(qClinet);
        Assert.isTrue(count <= 0, "客户端名称已存在，不能重复");
        //保存
        gatewayClientService.save(gatewayClientDO);
        customNacosConfigService.publishClientNacosConfig(gatewayClientDO.getId());
        return new ApiResult();
    }

    /**
     * 删除客户端
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult delete(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        GatewayClientDO dbGatewayClientDO = gatewayClientService.findById(id);
        Assert.notNull(dbGatewayClientDO, "未获取到对象");
        gatewayClientService.delete(dbGatewayClientDO);
        customNacosConfigService.publishClientNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 更新客户端
     * @param gatewayClientDO
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiResult update(@RequestBody GatewayClientDO gatewayClientDO) {
        Assert.notNull(gatewayClientDO, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(gatewayClientDO.getId()), "未获取到对象ID");
        gatewayClientDO.setUpdateTime(new Date());
        this.validate(gatewayClientDO);
        gatewayClientService.update(gatewayClientDO);
        customNacosConfigService.publishClientNacosConfig(gatewayClientDO.getId());
        return new ApiResult();
    }

    /**
     * 查询客户端
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult findById(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        return new ApiResult(gatewayClientService.findById(id));
    }

    /**
     * 分页查询客户端
     * @param clientReq
     * @return
     */
    @RequestMapping(value = "/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult pageList(@RequestBody GatewayClientDOReq clientReq) {
        GatewayClientDO gatewayClientDO = new GatewayClientDO();
        Integer reqCurrentPage = null;
        Integer reqPageSize = null;
        if (clientReq != null) {
            reqCurrentPage = clientReq.getCurrentPage();
            reqPageSize = clientReq.getPageSize();
            BeanUtils.copyProperties(clientReq, gatewayClientDO);
            if (StringUtils.isBlank(gatewayClientDO.getName())) {
                gatewayClientDO.setName(null);
            }
            if (StringUtils.isBlank(gatewayClientDO.getIp())) {
                gatewayClientDO.setIp(null);
            }
            if (StringUtils.isBlank(gatewayClientDO.getGroupCode())) {
                gatewayClientDO.setGroupCode(null);
            }
            if (StringUtils.isBlank(gatewayClientDO.getStatus())) {
                gatewayClientDO.setStatus(null);
            }
        }
        int currentPage = getCurrentPage(reqCurrentPage);
        int pageSize = getPageSize(reqPageSize);
        return new ApiResult(gatewayClientService.pageList(gatewayClientDO, currentPage, pageSize));
    }

    /**
     * 设置客户端状态为启用
     * @param id
     * @return
     */
    @RequestMapping(value = "/start", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult start(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        GatewayClientDO dbGatewayClientDO = gatewayClientService.findById(id);
        dbGatewayClientDO.setStatus(Constants.YES);
        gatewayClientService.update(dbGatewayClientDO);
        customNacosConfigService.publishClientNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 设置客户端状态为禁用
     * @param id
     * @return
     */
    @RequestMapping(value = "/stop", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult stop(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        GatewayClientDO dbGatewayClientDO = gatewayClientService.findById(id);
        dbGatewayClientDO.setStatus(Constants.NO);
        gatewayClientService.update(dbGatewayClientDO);
        customNacosConfigService.publishClientNacosConfig(id);
        return new ApiResult();
    }

}
