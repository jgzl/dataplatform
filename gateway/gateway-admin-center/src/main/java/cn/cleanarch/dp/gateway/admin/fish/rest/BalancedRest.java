package cn.cleanarch.dp.gateway.admin.fish.rest;

import cn.cleanarch.dp.common.gateway.ext.base.BaseRest;
import cn.cleanarch.dp.common.gateway.ext.vo.BalancedReq;
import cn.cleanarch.dp.common.gateway.ext.vo.BalancedRsp;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Balanced;
import cn.cleanarch.dp.common.gateway.ext.dataobject.LoadServer;
import cn.cleanarch.dp.common.gateway.ext.service.BalancedService;
import cn.cleanarch.dp.common.gateway.ext.service.CustomNacosConfigService;
import cn.cleanarch.dp.common.gateway.ext.service.LoadServerService;
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
public class BalancedRest extends BaseRest {

    @Resource
    private BalancedService balancedService;

    @Resource
    private LoadServerService loadServerService;

    @Resource
    private CustomNacosConfigService customNacosConfigService;

    /**
     * 添加负载配置
     * @param balancedReq
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ApiResult add(@RequestBody BalancedReq balancedReq) {
        Assert.notNull(balancedReq, "未获取到对象");
        Balanced balanced = new Balanced();
        balanced.setId(UUIDUtils.getUUIDString());
        balanced.setName(balancedReq.getName());
        balanced.setGroupCode(balancedReq.getGroupCode());
        balanced.setLoadUri(balancedReq.getLoadUri());
        balanced.setStatus(balancedReq.getStatus());
        balanced.setRemarks(balancedReq.getRemarks());
        balanced.setCreateTime(new Date());
        this.validate(balanced);

        //验证名称是否重复
        Balanced qBalanced = new Balanced();
        qBalanced.setName(balanced.getName());
        long count = balancedService.count(qBalanced);
        Assert.isTrue(count <= 0, "负载名称已存在，不能重复");
        //保存
        balancedService.save(balanced);
        //保存注册的服务列表
        List<LoadServer> serverList = balancedReq.getServerList();
        if (!CollectionUtils.isEmpty(serverList)) {
            for (LoadServer loadServer : serverList) {
                loadServer.setBalancedId(balanced.getId());
                loadServer.setCreateTime(new Date());
                loadServerService.save(loadServer);
            }
            //this.setRouteCacheVersion();
            customNacosConfigService.publishBalancedNacosConfig(balanced.getId());
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
        balancedService.deleteAndServer(id);
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
    public ApiResult update(@RequestBody BalancedReq balancedReq) {
        Assert.notNull(balancedReq, "未获取到对象");
        Assert.isTrue(StringUtils.isNotBlank(balancedReq.getId()), "未获取到对象ID");
        this.validate(balancedReq);
        Balanced balanced = balancedService.findById(balancedReq.getId());
        if (balanced != null) {
            balanced.setName(balancedReq.getName());
            balanced.setGroupCode(balancedReq.getGroupCode());
            balanced.setLoadUri(balancedReq.getLoadUri());
            balanced.setStatus(balancedReq.getStatus());
            balanced.setRemarks(balancedReq.getRemarks());
            balanced.setUpdateTime(new Date());
            balancedService.update(balanced);
            loadServerService.updates(balanced.getId(), balancedReq.getServerList());
            //this.setRouteCacheVersion();
            customNacosConfigService.publishBalancedNacosConfig(balanced.getId());
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
        Balanced balanced = balancedService.findById(id);
        if (balanced != null) {
            List<LoadServer> serverList = loadServerService.queryByBalancedId(id);
            BalancedRsp balancedRsp = new BalancedRsp();
            balancedRsp.setBalanced(balanced);
            balancedRsp.setServerList(serverList);
            return new ApiResult(balancedRsp);
        }
        return new ApiResult(Constants.FAILED, "未获取到对象", null);
    }

    /**
     * 分页查询
     * @param balancedReq
     * @return
     */
    @RequestMapping(value = "/pageList", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult pageList(@RequestBody BalancedReq balancedReq) {
        Balanced balanced = new Balanced();
        if (balancedReq != null){
            if (StringUtils.isNotBlank(balancedReq.getName())) {
                balanced.setName(balancedReq.getName());
            }
            if (StringUtils.isNotBlank(balancedReq.getStatus())) {
                balanced.setStatus(balancedReq.getStatus());
            }
            if (StringUtils.isNotBlank(balancedReq.getGroupCode())) {
                balanced.setGroupCode(balancedReq.getGroupCode());
            }
        }
        int currentPage = getCurrentPage(balancedReq.getCurrentPage());
        int pageSize = getPageSize(balancedReq.getPageSize());
        return new ApiResult(balancedService.pageList(balanced, currentPage, pageSize));
    }

    /**
     * 将状态置为：启用
     * @param id
     * @return
     */
    @RequestMapping(value = "/start", method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResult start(@RequestParam String id) {
        Assert.isTrue(StringUtils.isNotBlank(id), "未获取到对象ID");
        Balanced dbBalanced = balancedService.findById(id);
        dbBalanced.setStatus(Constants.YES);
        balancedService.update(dbBalanced);
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
        Balanced dbBalanced = balancedService.findById(id);
        dbBalanced.setStatus(Constants.NO);
        balancedService.update(dbBalanced);
        //this.setRouteCacheVersion();
        customNacosConfigService.publishBalancedNacosConfig(id);
        return new ApiResult();
    }

}
