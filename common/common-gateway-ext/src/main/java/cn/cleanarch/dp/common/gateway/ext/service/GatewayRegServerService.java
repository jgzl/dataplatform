package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.dao.GatewayRegServerDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRegServerDO;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.PageResult;
import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description 注册服务业务管理类
 * @Author jianglong
 * @Date 2020/05/16
 * @Version V1.0
 */
@Service
public class GatewayRegServerService extends BaseService<GatewayRegServerDO,Long, GatewayRegServerDao> {

    @Autowired
    private GatewayRegServerDao gatewayRegServerDao;

    private static final String IS_TIMEOUT = "isTimeout";
    private static final String TOKEN_EFFECTIVE_TIME = "tokenEffectiveTime";

    /**
     * 停止客户端下所有路由服务的访问（状态置为1，禁止通行）
     * @param clientId
     */
    public void stopClientAllRoute(String clientId){
        gatewayRegServerDao.setClientAllRouteStatus(clientId, Constants.YES, Constants.NO);
    }

    /**
     * 启动客户端下所有路由服务的访问（状态置为0，允许通行）
     * @param clientId
     */
    public void startClientAllRoute(String clientId){
        gatewayRegServerDao.setClientAllRouteStatus(clientId, Constants.NO, Constants.YES);
    }

    /**
     * 停止路由服务下所有客户端的访问（状态置为1，禁止通行）
     * @param routeId
     */
    public void stopRouteAllClient(String routeId){
        gatewayRegServerDao.setRouteAllClientStatus(routeId, Constants.YES, Constants.NO);
    }

    /**
     * 启动路由服务下所有客户端的访问（状态置为0，允许通行）
     * @param routeId
     */
    public void startRouteAllClient(String routeId){
        gatewayRegServerDao.setRouteAllClientStatus(routeId, Constants.NO, Constants.YES);
    }

    /**
     * 查询当所有已注册的客户端
     * @return
     */
    public List allRegClientList(){
        return gatewayRegServerDao.allRegClientList();
    }

    /**
     * 查询指定客户端注册的所有网关路由服务
     * @param clientId
     * @return
     */
    public List getRegClientList(String clientId){
        return gatewayRegServerDao.getRegClientList(clientId);
    }

    /**
     * 查询指定网关服务下的注册的所有客户端
     * @param routeId
     * @return
     */
    public List getByRouteRegClientList(String routeId){
        return gatewayRegServerDao.getByRouteRegClientList(routeId);
    }

    /**
     * 查询当前网关路由服务下已注册的客户端
     * @param gatewayRegServerDO
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String,Object>> regClientList(GatewayRegServerDO gatewayRegServerDO){
        String sql = "SELECT s.id AS regServerId,s.status AS regServerStatus,DATE_FORMAT(s.createTime,'%Y-%m-%d %H:%i:%s') as regServerTime,c.* FROM gateway_client c, gateway_regserver s WHERE c.id = s.clientId AND s.routeId=?";
        return nativeQuery(sql, Collections.singletonList(gatewayRegServerDO.getRouteId()));
    }

    /**
     * 查询当前网关路由服务下已注册的客户端
     * @param gatewayRegServerDO
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult clientPageList(GatewayRegServerDO gatewayRegServerDO, int currentPage, int pageSize){
        //DATE_FORMAT(s.tokenEffectiveTime,'%Y-%m-%d %H:%i:%s') as tokenEffectiveTime
        String sql = "SELECT s.id AS regServerId,s.status AS regServerStatus,DATE_FORMAT(s.createTime,'%Y-%m-%d %H:%i:%s') as regServerTime,s.token,s.secretKey,s.tokenEffectiveTime,c.* FROM gateway_client c, gateway_regserver s WHERE c.id = s.clientId AND s.routeId=?";
        PageResult pageResult = pageNativeQuery(sql, Collections.singletonList(gatewayRegServerDO.getRouteId()), currentPage, pageSize);
        List<Map<String, Object>> list = pageResult.getLists();
        if (list != null){
            long nowTime = System.currentTimeMillis();
            for (Map<String, Object> map : list){
                Object tokenEffectiveTime = map.get(TOKEN_EFFECTIVE_TIME);
                if (tokenEffectiveTime instanceof Timestamp timestamp){
                    map.put(TOKEN_EFFECTIVE_TIME, DateFormatUtils.format(new Date(timestamp.getTime()), Constants.YYYY_MM_DD_HH_MM_SS));
                    if (timestamp.getTime() < nowTime){
                        map.put(IS_TIMEOUT, Constants.NO);
                        continue;
                    }
                }
                map.put(IS_TIMEOUT, Constants.YES);
            }
        }
        return pageResult;
    }

    /**
     * 查询当前客户端已注册的网关路由服务
     * @param gatewayRegServerDO
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult serverPageList(GatewayRegServerDO gatewayRegServerDO, int currentPage, int pageSize){
        String sql = "SELECT s.id AS regServerId,s.status as regServerStatus,DATE_FORMAT(s.createTime,'%Y-%m-%d %H:%i:%s') as regServerTime,r.* FROM gateway_route r, gateway_regserver s WHERE r.id = s.routeId and s.clientId=?";
        return pageNativeQuery(sql, Collections.singletonList(gatewayRegServerDO.getClientId()), currentPage, pageSize);
    }

    /**
     * 查询当前网关路由服务下没有注册的客户端
     * @param gatewayRegServerDO
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult notRegClientPageList(GatewayRegServerDO gatewayRegServerDO, int currentPage, int pageSize){
        String sql = "SELECT c.id,c.name,c.groupCode,c.ip FROM gateway_client c WHERE c.status='0' AND id NOT IN (SELECT s.clientId FROM gateway_regserver s WHERE s.routeId=?)";
        return pageNativeQuery(sql, Collections.singletonList(gatewayRegServerDO.getRouteId()), currentPage, pageSize);
    }

    /**
     * 查询当前客户端没有注册的网关路由服务
     * @param gatewayRegServerDO
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult notRegServerPageList(GatewayRegServerDO gatewayRegServerDO, int currentPage, int pageSize){
        String sql = "SELECT r.id,r.name,r.uri,r.path,r.status FROM gateway_route r WHERE r.status='0' AND r.id NOT IN (SELECT s.routeId FROM gateway_regserver s WHERE s.clientId=?)";
        return pageNativeQuery(sql, Collections.singletonList(gatewayRegServerDO.getClientId()), currentPage, pageSize);
    }


}
