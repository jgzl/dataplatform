package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewayRouteDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayMonitorDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayRouteDORsp;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayMonitorReq;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewayMonitorDao;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 告警监控业务类
 * @Author JL
 * @Date 2021/04/14
 * @Version V1.0
 */
@Service
public class GatewayMonitorService extends BaseService<GatewayMonitorDO, String, GatewayMonitorDao> {

    @Resource
    private GatewayRouteDao gatewayRouteDao;
    @Resource
    private GatewayMonitorDao gatewayMonitorDao;

    /**
     * 获取监控的服务列表
     * @param gatewayMonitorReq
     * @return
     */
    public List<GatewayRouteDO> list(GatewayMonitorReq gatewayMonitorReq){
        GatewayRouteDO queryGatewayRouteDO = new GatewayRouteDO();
        if (gatewayMonitorReq != null && StringUtils.isNotBlank(gatewayMonitorReq.getStatus())) {
            //如果前端搜索状态为2告警类型，则直查询路由网关状态的为0的记录
            //网关路由服务，只有0正常，1禁用，两种状态
            //网关路由服务监控，有0正常，1禁用，2告警三种状态
            if (Constants.ALARM.equals(gatewayMonitorReq.getStatus())){
                queryGatewayRouteDO.setStatus(Constants.YES);
            }else {
                queryGatewayRouteDO.setStatus(gatewayMonitorReq.getStatus());
            }
        }
        List<GatewayMonitorDO> gatewayMonitorDOList = this.validMonitorList();
        //没有监控数据
        if (CollectionUtils.isEmpty(gatewayMonitorDOList)){
            return null;
        }
        Map<String, GatewayMonitorDO> monitorMap = gatewayMonitorDOList.stream().collect(Collectors.toMap(GatewayMonitorDO::getId, r -> r));
        List<GatewayRouteDO> resultList = new ArrayList<>(monitorMap.size());
        List<GatewayRouteDO> gatewayRouteDOList = gatewayRouteDao.findAll(Example.of(queryGatewayRouteDO, ExampleMatcher.matching()), Sort.by(Sort.Order.desc("id")));
        for (GatewayRouteDO gatewayRouteDO : gatewayRouteDOList){
            GatewayMonitorDO gatewayMonitorDO = monitorMap.get(gatewayRouteDO.getId());
            if (gatewayMonitorDO == null){
                continue;
            }
            //如果监控状态值不等于0和1，其它状态值则表示存在告警
            if (StringUtils.equalsAny(gatewayMonitorDO.getStatus(), Constants.YES, Constants.NO)){
            }else {
                //如果前端搜索状态为：0正常，1禁用 的网关路由服务，则不显示告警状态的网关路由服务
                if (StringUtils.equalsAny(gatewayMonitorReq.getStatus(), Constants.YES, Constants.NO)){
                    continue;
                }
                gatewayRouteDO.setStatus(gatewayMonitorDO.getStatus());
            }
            GatewayRouteDORsp routeRsp = new GatewayRouteDORsp();
            BeanUtils.copyProperties(gatewayRouteDO, routeRsp);
            routeRsp.setGatewayMonitorDO(gatewayMonitorDO);
            resultList.add(routeRsp);
        }
        return resultList;
    }

    /**
     * 获取监控配置，告警状态：0启用，1禁用，2告警
     * @return
     */
    public List<GatewayMonitorDO> validMonitorList(){
        return gatewayMonitorDao.validMonitorList();
    }

    /**
     * 获取0正常状态的网关路由服务监控配置，告警状态：0启用，1禁用，2告警
     * @return
     */
    public List<GatewayMonitorDO> validRouteMonitorList(){
        return gatewayMonitorDao.validRouteMonitorList();
    }

}
