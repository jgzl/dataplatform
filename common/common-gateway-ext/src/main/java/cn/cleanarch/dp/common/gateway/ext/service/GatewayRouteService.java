package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.dao.GatewayMonitorDao;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewayRouteDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRegServerDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayMonitorDO;
import cn.cleanarch.dp.common.gateway.ext.util.PageResult;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayRouteDORsp;
import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 路由管理业务类
 * @Author jianglong
 * @Date 2020/05/14
 * @Version V1.0
 */
@Service
public class GatewayRouteService extends BaseService<GatewayRouteDO,String, GatewayRouteDao> {

    @Resource
    private GatewayRegServerService gatewayRegServerService;

    @Resource
    private GatewayMonitorDao gatewayMonitorDao;

    @Resource
    private GatewayRouteDao gatewayRouteDao;

    /**
     * 删除网关路由以及已注册的客户端（关联表）
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void delete(String id){
        GatewayRouteDO gatewayRouteDO = this.findById(id);
        if (gatewayRouteDO != null) {
            GatewayRegServerDO gatewayRegServerDO = new GatewayRegServerDO();
            gatewayRegServerDO.setRouteId(id);
            List<GatewayRegServerDO> gatewayRegServerDOList = gatewayRegServerService.findAll(gatewayRegServerDO);
            //删除服务列表
            if (gatewayRegServerDOList != null && gatewayRegServerDOList.size()>0) {
                gatewayRegServerService.deleteInBatch(gatewayRegServerDOList);
            }
            //删除监控配置
            GatewayMonitorDO gatewayMonitorDO = gatewayMonitorDao.getReferenceById(id);
            if (gatewayMonitorDO != null){
                gatewayMonitorDao.deleteById(id);
            }
            //删除路由对象
            this.delete(gatewayRouteDO);
        }
    }

    /**
     * 获取需要监控的网关路由服务
     * @return
     */
    public List<GatewayRouteDO> monitorRouteList(){
        return gatewayRouteDao.monitorRouteList();
    }

    /**
     * 分页查询
     * @param gatewayRouteDO
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<GatewayRouteDO> pageList(GatewayRouteDO gatewayRouteDO, int currentPage, int pageSize){
        //构造条件查询方式
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.isNotBlank(gatewayRouteDO.getName())) {
            //支持模糊条件查询
            matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        PageResult<GatewayRouteDO> result = this.pageList(gatewayRouteDO, matcher, currentPage, pageSize);
        List<GatewayRouteDO> gatewayRouteDOList = result.getLists();
        if (CollectionUtils.isEmpty(gatewayRouteDOList)){
            return result;
        }
        //获取所有监控配置
        List<GatewayMonitorDO> gatewayMonitorDOList = gatewayMonitorDao.findAll();
        if (CollectionUtils.isEmpty(gatewayMonitorDOList)){
            return result;
        }
        //将监控配置重新封装到数据集合中
        Map<String, GatewayMonitorDO> monitorMap = gatewayMonitorDOList.stream().collect(Collectors.toMap(GatewayMonitorDO::getId, m -> m));
        List<GatewayRouteDO> gatewayRouteDORspList = new ArrayList<>(gatewayRouteDOList.size());
        for (GatewayRouteDO gatewayRouteDO1 : gatewayRouteDOList){
            GatewayRouteDORsp routeRsp = new GatewayRouteDORsp();
            BeanUtils.copyProperties(gatewayRouteDO1, routeRsp);
            GatewayMonitorDO gatewayMonitorDO = monitorMap.get(gatewayRouteDO1.getId());
            if (gatewayMonitorDO != null){
                routeRsp.setMonitor(gatewayMonitorDO);
            }
            gatewayRouteDORspList.add(routeRsp);
        }
        result.setLists(gatewayRouteDORspList);
        return result;
    }
}
