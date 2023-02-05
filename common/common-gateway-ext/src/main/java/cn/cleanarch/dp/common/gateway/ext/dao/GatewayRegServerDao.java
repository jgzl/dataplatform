package cn.cleanarch.dp.common.gateway.ext.dao;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRegServerDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 注册网关服务Dao数据层操作接口
 * @Author jianglong
 * @Date 2020/05/16
 * @Version V1.0
 */
public interface GatewayRegServerDao extends JpaRepository<GatewayRegServerDO, Long> {

    /**
     * 修改客户端下所有已注册网关服务的状态
     * @param clientId
     * @param status
     * @param newStatus
     */
    @Query(value = "update GatewayRegServerDO set status=?3 where clientId=?1 and status=?2")
    @Modifying
    @Transactional(rollbackFor = {Throwable.class})
    void setClientAllRouteStatus(String clientId, String status, String newStatus);

    /**
     * 修改网关服务下所有已注册客户端的状态
     * @param routeId
     * @param status
     * @param newStatus
     */
    @Query(value = "update GatewayRegServerDO set status=?3 where routeId=?1 and status=?2")
    @Modifying
    @Transactional(rollbackFor = {Throwable.class})
    void setRouteAllClientStatus(String routeId, String status, String newStatus);

    /**
     * 查询指定网关服务下的注册的,并且是状态为0允许通行的
     * @return
     */
    @Query(value ="SELECT s.routeId,c.id,c.ip,s.token,s.secretKey FROM GatewayClientDO c, GatewayRegServerDO s, GatewayRouteDO r WHERE c.id = s.clientId AND r.id = s.routeId AND c.status='0' AND s.status='0' AND r.status='0'")
    List allRegClientList();

    /**
     * 查询指定客户端注册的所有网关路由服务
     * @return
     */
    @Query(value ="SELECT s.routeId,c.id,c.ip,s.token,s.secretKey,s.status FROM GatewayClientDO c, GatewayRegServerDO s WHERE c.id = s.clientId AND s.clientId=?1")
    List getRegClientList(String clientId);

    /**
     * 查询指定网关服务下的注册的所有客户端
     * @return
     */
    @Query(value ="SELECT s.routeId,c.id,c.ip,s.token,s.secretKey,s.status FROM GatewayClientDO c, GatewayRegServerDO s WHERE c.id = s.clientId AND s.routeId=?1")
    List getByRouteRegClientList(String routeId);

}
