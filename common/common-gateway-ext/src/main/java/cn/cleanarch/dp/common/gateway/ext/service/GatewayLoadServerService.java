package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewayLoadServerDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayBalancedDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayLoadServerDO;
import cn.cleanarch.dp.common.gateway.ext.util.PageResult;
import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 负载服务业务实现类
 * @Author jianglong
 * @Date 2020/06/28
 * @Version V1.0
 */
@Service
public class GatewayLoadServerService extends BaseService<GatewayLoadServerDO, Long, GatewayLoadServerDao> {

    @Resource
    private GatewayLoadServerDao gatewayLoadServerDao;

    /**
     * 查询当前负载网关已加配置路由服务
     * @param balancedId
     * @return
     */
    @Transactional(readOnly = true)
    public List loadServerList(String balancedId){
        return gatewayLoadServerDao.queryLoadServerList(balancedId);
    }

    /**
     * 查询所有路由服务
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult notLoadServerPageList(int currentPage, int pageSize){
        String sql ="SELECT r.id,r.name,r.groupCode,r.uri,r.path,r.method FROM gateway_route r WHERE r.status='0' ";
        return pageNativeQuery(sql, null, currentPage, pageSize);
    }

    /**
     *  删除指定负载下所有的路由服务
     * @param balancedId
     */
    public void deleteAllByBalancedId(String balancedId){
        gatewayLoadServerDao.deleteAllByBalancedId(balancedId);
    }

    /**
     * 查询指定负载下所有路由服务
     * @param balancedId
     * @return
     */
    public List<GatewayLoadServerDO> queryByBalancedId(String balancedId){
        return gatewayLoadServerDao.queryByBalancedId(balancedId);
    }

    /**
     * 查询指定路由关联的负载服务
     * @param routeId
     * @return
     */
    public List<GatewayLoadServerDO> queryByRouteId(String routeId){
        return gatewayLoadServerDao.queryByRouteId(routeId);
    }

    /**
     * 保存指定负载下的所有路由服务
     * @param serverList
     */
    public void updates(String balancedId, List<GatewayLoadServerDO> serverList){
        List<GatewayLoadServerDO> dbServerList = this.queryByBalancedId(balancedId);
        Map<Long, Integer> dbIdMap = new HashMap<>();
        for (GatewayLoadServerDO gatewayLoadServerDO : serverList){
            //新增
            if (gatewayLoadServerDO.getId() == null || gatewayLoadServerDO.getId() <= 0){
                gatewayLoadServerDO.setBalancedId(balancedId);
                gatewayLoadServerDO.setCreateTime(new Date());
                this.save(gatewayLoadServerDO);
            }else {
                //记录未变更的ID
                dbIdMap.put(gatewayLoadServerDO.getId(), gatewayLoadServerDO.getWeight());
            }
        }

        if (!CollectionUtils.isEmpty(dbServerList)){
            //idList中不存在，则需要删除数据库中，已经被前端取消绑定的服务
            for (GatewayLoadServerDO gatewayLoadServerDO : dbServerList){
                if (dbIdMap.get(gatewayLoadServerDO.getId()) == null){
                    this.deleteById(gatewayLoadServerDO.getId());
                }else {
                    //比较weight值是否有改变
                    Integer weight = dbIdMap.get(gatewayLoadServerDO.getId());
                    if (gatewayLoadServerDO.getWeight().intValue() != weight.intValue()){
                        gatewayLoadServerDO.setWeight(weight);
                        this.update(gatewayLoadServerDO);
                    }
                }
            }
        }
    }

    /**
     * 设置负载均衡网关路由配置
     * @param balancedDO
     * @param gatewayLoadServerDO
     * @param gatewayRouteDO
     */
    public void setBalancedRoute(GatewayBalancedDO balancedDO, GatewayLoadServerDO gatewayLoadServerDO, GatewayRouteDO gatewayRouteDO){
        //获取route，改变参数，构造一个新route对象
        String routeId = this.setBalancedRouteId(balancedDO.getId(), gatewayRouteDO.getId());
        gatewayRouteDO.setId(routeId);
        //设置断言路径
        gatewayRouteDO.setPath(GatewayRouteConstants.PARENT_PATH + balancedDO.getLoadUri());
        //设置负载参数,查找服务对应的路由服务
        String weightName = this.setBalancedWeightName(balancedDO.getId());
        gatewayRouteDO.setWeightName(weightName);
        //设置负载权重值
        gatewayRouteDO.setWeight(gatewayLoadServerDO.getWeight());
        //默认负载的断言路径截取级别为1
        gatewayRouteDO.setStripPrefix(1);
    }

    /**
     * 设置负载均衡网关路由的WeightName
     * @param balancedId
     * @return
     */
    public String setBalancedWeightName(String balancedId){
        return GatewayRouteConstants.BALANCED + "-" + balancedId.replaceAll("-","");
    }

    /**
     * 设置负载均衡网关路由ID
     * @param balancedId
     * @param routeId
     * @return
     */
    public String setBalancedRouteId(String balancedId, String routeId){
        return setBalancedWeightName(balancedId) + "-" + routeId;
    }

}
