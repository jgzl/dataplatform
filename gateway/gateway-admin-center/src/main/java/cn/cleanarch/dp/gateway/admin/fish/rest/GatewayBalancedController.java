package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayBalancedReq;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayBalancedRsp;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayBalancedDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayLoadServerDO;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayBalancedService;
import cn.cleanarch.dp.common.gateway.ext.service.CustomNacosConfigService;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayLoadServerService;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description 负载配置管理控制器
 * @Author jianglong
 * @Date 2020/06/28
 * @Version V1.0
 */
@Slf4j
@RestController
@RequestMapping("/gateway/balanced")
public class GatewayBalancedController extends BaseRest {

    @Resource
    private GatewayBalancedService gatewayBalancedService;

    @Resource
    private GatewayLoadServerService gatewayLoadServerService;

    @Resource
    private CustomNacosConfigService customNacosConfigService;

    /**
     * 添加负载配置
     * @param balancedReq
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ApiResult add(@RequestBody GatewayBalancedReq balancedReq) {
        Assert.notNull(balancedReq, "未获取到对象");
        GatewayBalancedDO balancedDO = new GatewayBalancedDO();
        balancedDO.setId(UUIDUtils.getUUIDString());
        balancedDO.setName(balancedReq.getName());
        balancedDO.setGroupCode(balancedReq.getGroupCode());
        balancedDO.setLoadUri(balancedReq.getLoadUri());
        balancedDO.setStatus(balancedReq.getStatus());
        balancedDO.setRemarks(balancedReq.getRemarks());
        balancedDO.setCreateTime(new Date());
        this.validate(balancedDO);

        //验证名称是否重复
        GatewayBalancedDO qBalancedDO = new GatewayBalancedDO();
        qBalancedDO.setName(balancedDO.getName());
        long count = gatewayBalancedService.count(qBalancedDO);
        Assert.isTrue(count <= 0, "负载名称已存在，不能重复");
        //保存
        gatewayBalancedService.save(balancedDO);
        //保存注册的服务列表
        List<GatewayLoadServerDO> serverList = balancedReq.getServerList();
        if (!CollectionUtils.isEmpty(serverList)) {
            for (GatewayLoadServerDO gatewayLoadServerDO : serverList) {
                gatewayLoadServerDO.setBalancedId(balancedDO.getId());
                gatewayLoadServerDO.setCreateTime(new Date());
                gatewayLoadServerService.save(gatewayLoadServerDO);
            }
            //this.setRouteCacheVersion();
            customNacosConfigService.publishBalancedNacosConfig(balancedDO.getId());
        }
        return new ApiResult();
    }

    /**
     * 删除指定负载ID配置
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult delete(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        gatewayBalancedService.deleteAndServer(id);
        //this.setRouteCacheVersion();
        customNacosConfigService.publishBalancedNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 更新负载数据对象
     * @param balancedReq
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiResult update(@RequestBody GatewayBalancedReq balancedReq) {
        Assert.notNull(balancedReq, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(balancedReq.getId()), "未获取到对象ID");
        this.validate(balancedReq);
        GatewayBalancedDO balancedDO = gatewayBalancedService.findById(balancedReq.getId());
        if (balancedDO != null) {
            balancedDO.setName(balancedReq.getName());
            balancedDO.setGroupCode(balancedReq.getGroupCode());
            balancedDO.setLoadUri(balancedReq.getLoadUri());
            balancedDO.setStatus(balancedReq.getStatus());
            balancedDO.setRemarks(balancedReq.getRemarks());
            balancedDO.setUpdateTime(new Date());
            gatewayBalancedService.update(balancedDO);
            gatewayLoadServerService.updates(balancedDO.getId(), balancedReq.getServerList());
            //this.setRouteCacheVersion();
            customNacosConfigService.publishBalancedNacosConfig(balancedDO.getId());
        }
        return new ApiResult();
    }

    /**
     * 获取指定负载ID对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult findById(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        GatewayBalancedDO balancedDO = gatewayBalancedService.findById(id);
        if (balancedDO != null) {
            List<GatewayLoadServerDO> serverList = gatewayLoadServerService.queryByBalancedId(id);
            GatewayBalancedRsp gatewayBalancedRsp = new GatewayBalancedRsp();
            gatewayBalancedRsp.setBalanced(balancedDO);
            gatewayBalancedRsp.setServerList(serverList);
            return new ApiResult(gatewayBalancedRsp);
        }
        return new ApiResult(Constants.FAILED, "未获取到对象", null);
    }

    /**
     * 分页查询
     * @param balancedReq
     * @return
     */
    @RequestMapping(value = "/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult pageList(@RequestBody GatewayBalancedReq balancedReq) {
        GatewayBalancedDO balancedDO = new GatewayBalancedDO();
        if (balancedReq != null){
            if (StringUtils.isNotBlank(balancedReq.getName())) {
                balancedDO.setName(balancedReq.getName());
            }
            if (StringUtils.isNotBlank(balancedReq.getStatus())) {
                balancedDO.setStatus(balancedReq.getStatus());
            }
            if (StringUtils.isNotBlank(balancedReq.getGroupCode())) {
                balancedDO.setGroupCode(balancedReq.getGroupCode());
            }
        }
        int currentPage = getCurrentPage(balancedReq.getCurrentPage());
        int pageSize = getPageSize(balancedReq.getPageSize());
        return new ApiResult(gatewayBalancedService.pageList(balancedDO, currentPage, pageSize));
    }

    /**
     * 将状态置为：启用
     * @param id
     * @return
     */
    @RequestMapping(value = "/start", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult start(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        GatewayBalancedDO dbBalancedDO = gatewayBalancedService.findById(id);
        dbBalancedDO.setStatus(Constants.YES);
        gatewayBalancedService.update(dbBalancedDO);
        //this.setRouteCacheVersion();
        customNacosConfigService.publishBalancedNacosConfig(id);
        return new ApiResult();
    }

    /**
     * 将状态置为：禁用
     * @param id
     * @return
     */
    @RequestMapping(value = "/stop", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult stop(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        GatewayBalancedDO dbBalancedDO = gatewayBalancedService.findById(id);
        dbBalancedDO.setStatus(Constants.NO);
        gatewayBalancedService.update(dbBalancedDO);
        //this.setRouteCacheVersion();
        customNacosConfigService.publishBalancedNacosConfig(id);
        return new ApiResult();
    }

}
