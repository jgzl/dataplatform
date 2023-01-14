package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.RouteDao;
import cn.cleanarch.dp.common.gateway.ext.vo.MonitorReq;
import cn.cleanarch.dp.common.gateway.ext.vo.RouteRsp;
import cn.cleanarch.dp.common.gateway.ext.dao.MonitorDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Monitor;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Route;
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
public class MonitorService extends BaseService<Monitor, String, MonitorDao> {

    @Resource
    private RouteDao routeDao;
    @Resource
    private MonitorDao monitorDao;

    /**
     * 获取监控的服务列表
     * @param monitorReq
     * @return
     */
    public List<Route> list(MonitorReq monitorReq){
        Route queryRoute = new Route();
        if (monitorReq != null && StringUtils.isNotBlank(monitorReq.getStatus())) {
            //如果前端搜索状态为2告警类型，则直查询路由网关状态的为0的记录
            //网关路由服务，只有0正常，1禁用，两种状态
            //网关路由服务监控，有0正常，1禁用，2告警三种状态
            if (Constants.ALARM.equals(monitorReq.getStatus())){
                queryRoute.setStatus(Constants.YES);
            }else {
                queryRoute.setStatus(monitorReq.getStatus());
            }
        }
        List<Monitor> monitorList = this.validMonitorList();
        //没有监控数据
        if (CollectionUtils.isEmpty(monitorList)){
            return null;
        }
        Map<String, Monitor> monitorMap = monitorList.stream().collect(Collectors.toMap(Monitor::getId, r -> r));
        List<Route> resultList = new ArrayList<>(monitorMap.size());
        List<Route> routeList = routeDao.findAll(Example.of(queryRoute, ExampleMatcher.matching()), Sort.by(Sort.Direction.ASC));
        for (Route route : routeList){
            Monitor monitor = monitorMap.get(route.getId());
            if (monitor == null){
                continue;
            }
            //如果监控状态值不等于0和1，其它状态值则表示存在告警
            if (StringUtils.equalsAny(monitor.getStatus(), Constants.YES, Constants.NO)){
            }else {
                //如果前端搜索状态为：0正常，1禁用 的网关路由服务，则不显示告警状态的网关路由服务
                if (StringUtils.equalsAny(monitorReq.getStatus(), Constants.YES, Constants.NO)){
                    continue;
                }
                route.setStatus(monitor.getStatus());
            }
            RouteRsp routeRsp = new RouteRsp();
            BeanUtils.copyProperties(route, routeRsp);
            routeRsp.setMonitor(monitor);
            resultList.add(routeRsp);
        }
        return resultList;
    }

    /**
     * 获取监控配置，告警状态：0启用，1禁用，2告警
     * @return
     */
    public List<Monitor> validMonitorList(){
        return monitorDao.validMonitorList();
    }

    /**
     * 获取0正常状态的网关路由服务监控配置，告警状态：0启用，1禁用，2告警
     * @return
     */
    public List<Monitor> validRouteMonitorList(){
        return monitorDao.validRouteMonitorList();
    }

}
