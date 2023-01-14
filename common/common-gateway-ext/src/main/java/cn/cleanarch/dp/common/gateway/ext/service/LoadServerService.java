package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.LoadServerDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Balanced;
import cn.cleanarch.dp.common.gateway.ext.dataobject.LoadServer;
import cn.cleanarch.dp.common.gateway.ext.dataobject.Route;
import cn.cleanarch.dp.common.gateway.ext.util.PageResult;
import cn.cleanarch.dp.common.gateway.ext.util.RouteConstants;
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
public class LoadServerService extends BaseService<LoadServer, Long, LoadServerDao> {

    @Resource
    private LoadServerDao loadServerDao;

    /**
     * 查询当前负载网关已加配置路由服务
     * @param balancedId
     * @return
     */
    @Transactional(readOnly = true)
    public List loadServerList(String balancedId){
        return loadServerDao.queryLoadServerList(balancedId);
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
        loadServerDao.deleteAllByBalancedId(balancedId);
    }

    /**
     * 查询指定负载下所有路由服务
     * @param balancedId
     * @return
     */
    public List<LoadServer> queryByBalancedId(String balancedId){
        return loadServerDao.queryByBalancedId(balancedId);
    }

    /**
     * 查询指定路由关联的负载服务
     * @param routeId
     * @return
     */
    public List<LoadServer> queryByRouteId(String routeId){
        return loadServerDao.queryByRouteId(routeId);
    }

    /**
     * 保存指定负载下的所有路由服务
     * @param serverList
     */
    public void updates(String balancedId, List<LoadServer> serverList){
        List<LoadServer> dbServerList = this.queryByBalancedId(balancedId);
        Map<Long, Integer> dbIdMap = new HashMap<>();
        for (LoadServer loadServer : serverList){
            //新增
            if (loadServer.getId() == null || loadServer.getId() <= 0){
                loadServer.setBalancedId(balancedId);
                loadServer.setCreateTime(new Date());
                this.save(loadServer);
            }else {
                //记录未变更的ID
                dbIdMap.put(loadServer.getId(), loadServer.getWeight());
            }
        }

        if (!CollectionUtils.isEmpty(dbServerList)){
            //idList中不存在，则需要删除数据库中，已经被前端取消绑定的服务
            for (LoadServer loadServer : dbServerList){
                if (dbIdMap.get(loadServer.getId()) == null){
                    this.deleteById(loadServer.getId());
                }else {
                    //比较weight值是否有改变
                    Integer weight = dbIdMap.get(loadServer.getId());
                    if (loadServer.getWeight().intValue() != weight.intValue()){
                        loadServer.setWeight(weight);
                        this.update(loadServer);
                    }
                }
            }
        }
    }

    /**
     * 设置负载均衡网关路由配置
     * @param balanced
     * @param loadServer
     * @param route
     */
    public void setBalancedRoute(Balanced balanced, LoadServer loadServer, Route route){
        //获取route，改变参数，构造一个新route对象
        String routeId = this.setBalancedRouteId(balanced.getId(), route.getId());
        route.setId(routeId);
        //设置断言路径
        route.setPath(RouteConstants.PARENT_PATH + balanced.getLoadUri());
        //设置负载参数,查找服务对应的路由服务
        String weightName = this.setBalancedWeightName(balanced.getId());
        route.setWeightName(weightName);
        //设置负载权重值
        route.setWeight(loadServer.getWeight());
        //默认负载的断言路径截取级别为1
        route.setStripPrefix(1);
    }

    /**
     * 设置负载均衡网关路由的WeightName
     * @param balancedId
     * @return
     */
    public String setBalancedWeightName(String balancedId){
        return RouteConstants.BALANCED + "-" + balancedId.replaceAll("-","");
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
