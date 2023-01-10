package cn.cleanarch.dp.gateway.admin.service;

import cn.cleanarch.dp.gateway.dataobject.GatewayRouteDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 路由
 *
 * @author li7hai26@gmail.com
 * @date 2018-11-06 10:17:18
 */
public interface GatewayRouteService extends IService<GatewayRouteDO> {

    /**
     * 删除路由信息
     *
     * @param routeId 路由id
     * @return
     */
	void deleteRoute(String routeId);
}
