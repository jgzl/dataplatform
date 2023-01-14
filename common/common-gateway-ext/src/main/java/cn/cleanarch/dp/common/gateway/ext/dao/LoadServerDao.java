package cn.cleanarch.dp.common.gateway.ext.dao;

import cn.cleanarch.dp.common.gateway.ext.dataobject.LoadServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @Description 负载服务数据层操作接口
 * @Author jianglong
 * @Date 2020/06/28
 * @Version V1.0
 */
public interface LoadServerDao extends JpaRepository<LoadServer, Long> {

    /**
     * 删除负载下所有的路由服务
     * @param balancedId
     */
    void deleteAllByBalancedId(String balancedId);

    /**
     * 查询指定负载下所有路由服务
     * @param balancedId
     * @return
     */
    List<LoadServer> queryByBalancedId(String balancedId);

    /**
     * 查询指定路由关联的负载服务
     * @param routeId
     * @return
     */
    List<LoadServer> queryByRouteId(String routeId);

    @Query(value = "SELECT r.name,r.groupCode,r.uri,r.path,r.method,r.status,l.id,l.routeId,l.weight FROM gateway_route r INNER JOIN gateway_loadserver l ON r.id=l.routeId WHERE l.balancedId=?1",
            nativeQuery = true)
    List<Map> queryLoadServerList(String balancedId);


}
