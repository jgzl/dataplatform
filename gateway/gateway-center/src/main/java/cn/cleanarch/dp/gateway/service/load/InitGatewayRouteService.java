package cn.cleanarch.dp.gateway.service.load;

import cn.cleanarch.dp.common.gateway.ext.dao.GatewayRouteDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayBalancedDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayLoadServerDO;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayBalancedService;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayLoadServerService;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.gateway.cache.GatewayRouteCache;
import cn.cleanarch.dp.gateway.service.DynamicGatewayRouteService;
import cn.cleanarch.dp.gateway.service.LoadGatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 初始化负载均衡配置、网关路由配置
 * @Author JL
 * @Date 2021/09/22
 * @Version V1.0
 */
@Slf4j
@Service
public class InitGatewayRouteService {

    private final List<RouteDefinition> routeDefinitions = new ArrayList<>();

    @Resource
    private GatewayRouteDao gatewayRouteDao;
    @Resource
    private GatewayBalancedService balancedService;
    @Resource
    private LoadGatewayRouteService loadGatewayRouteService;
    @Resource
    private GatewayLoadServerService gatewayLoadServerService;
    @Resource
    private DynamicGatewayRouteService dynamicGatewayRouteService;

    /**
     * 初始化执行
     */
    @PostConstruct
    public void init(){
        //一定要清空routeDefinitions否则每次刷新会往集合中添加重复数据
        routeDefinitions.clear();
        initLoadRoute();
        initLoadBalanced();
        dynamicGatewayRouteService.addAll(routeDefinitions);
    }

    /**
     * 初始化完毕后，加载路由
     */
    public void initLoadRoute(){
        GatewayRouteDO query = new GatewayRouteDO();
        query.setStatus(Constants.YES);
        try {
            List<GatewayRouteDO> gatewayRouteDOList = gatewayRouteDao.findAll(Example.of(query));
            if (CollectionUtils.isEmpty(gatewayRouteDOList)) {
                log.error("初始化网关路由，无可用网关路由配置...");
                return ;
            }
            gatewayRouteDOList.forEach(r -> {
                GatewayRouteCache.put(r.getId(), r);
                routeDefinitions.add(loadGatewayRouteService.loadRouteDefinition(r));
            });
            log.info("初始化加载网关路由配置共{}条", gatewayRouteDOList.size());
        }catch(Exception e){
            log.error("加载数据库中网关路由配置异常：",e);
        }
    }

    /**
     * 初始化完毕后，加载负载路由
     */
    public void initLoadBalanced(){
        GatewayRouteDO query = new GatewayRouteDO();
        query.setStatus(Constants.YES);
        GatewayBalancedDO balancedDO = new GatewayBalancedDO();
        balancedDO.setStatus(Constants.YES);
        List<GatewayRouteDO> balancedGatewayRouteDOList = new ArrayList<>();
        List<GatewayBalancedDO> balancedDOList = balancedService.findAll(balancedDO);
        if (CollectionUtils.isEmpty(balancedDOList)) {
            log.info("初始化网关负载均衡路由，无可用配置...");
            return ;
        }
        //查找负载均衡关联的网关路由列表
        List<GatewayLoadServerDO> gatewayLoadServerDOList = gatewayLoadServerService.findAll(new GatewayLoadServerDO());
        if (CollectionUtils.isEmpty(gatewayLoadServerDOList)) {
            log.error("初始化网关负载均衡路由，未添加网关路由配置...");
            return ;
        }
        //查询所有可用的网关路由列表
        List<GatewayRouteDO> gatewayRouteDOList = gatewayRouteDao.findAll(Example.of(query));
        if (CollectionUtils.isEmpty(gatewayRouteDOList)){
            log.error("初始化网关负载均衡路由，无可用网关路由配置...");
            return ;
        }
        Map<String, GatewayRouteDO> routeMap =  gatewayRouteDOList.stream().collect(Collectors.toMap(GatewayRouteDO::getId, r->r));
        // 将同一个负载下的网关路由放在同一个集合中(注意：过滤掉权重值为0的网关，表示无流量流入，无需创建网关)
        Map<String, List<GatewayLoadServerDO>> serverRouteMap = gatewayLoadServerDOList.stream().filter(s->s.getWeight()>0)
                .collect(Collectors.toMap(GatewayLoadServerDO::getBalancedId,
                    l-> new ArrayList<GatewayLoadServerDO>(){
                        {add(l);}
                    },
                    (List<GatewayLoadServerDO> v1, List<GatewayLoadServerDO> v2) ->{
                        v1.addAll(v2);
                        return v1;
                    })
                );
        try {
            // 遍历负载均衡配置，组装生成新的route结构，并加载到gateway网关路由集合中
            for (GatewayBalancedDO b : balancedDOList){
                //查找负载下注册的服务
                List<GatewayLoadServerDO> serverList = serverRouteMap.get(b.getId());
                if (CollectionUtils.isEmpty(serverList)) {
                    continue;
                }
                serverList.forEach(s -> {
                    GatewayRouteDO gatewayRouteDO = routeMap.get(s.getRouteId());
                    if (gatewayRouteDO != null) {
                        GatewayRouteDO bGatewayRouteDO = new GatewayRouteDO();
                        BeanUtils.copyProperties(gatewayRouteDO, bGatewayRouteDO);
                        gatewayLoadServerService.setBalancedRoute(b, s, bGatewayRouteDO);
                        //添加新路由集合中
                        balancedGatewayRouteDOList.add(bGatewayRouteDO);
                    }
                });
            }
            //将新的路由加载网关路由集合中
            balancedGatewayRouteDOList.forEach(r->{
                //记录到本地缓存中
                GatewayRouteCache.put(r.getId(), r);
                //添加新的负载均衡路由对象
                routeDefinitions.add(loadGatewayRouteService.loadRouteDefinition(r));
            });
        }catch(Exception e){
            log.error("加载数据库中网关负载路由配置异常：",e);
        }
        log.info("初始化加载网关负载路由配置共{}条", balancedGatewayRouteDOList.size());
    }

}
