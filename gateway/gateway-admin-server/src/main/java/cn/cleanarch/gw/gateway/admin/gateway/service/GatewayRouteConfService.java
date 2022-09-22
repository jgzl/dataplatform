package cn.cleanarch.gw.gateway.admin.gateway.service;

import cn.cleanarch.gw.gateway.admin.gateway.domain.GatewayRouteConfDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 路由
 *
 * @author li7hai26@gmail.com
 * @date 2018-11-06 10:17:18
 */
public interface GatewayRouteConfService extends IService<GatewayRouteConfDO> {

    /**
     * 删除路由信息
     *
     * @param routeId 路由id
     * @return
     */
	void deleteRoute(String routeId);
}
