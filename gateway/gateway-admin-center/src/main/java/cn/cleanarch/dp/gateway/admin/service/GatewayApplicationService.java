package cn.cleanarch.dp.gateway.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.cleanarch.dp.gateway.dataobject.GatewayApplicationDO;

 /**
 * 网关管理-应用服务表;(GW_GATEWAY_APPLICATION)表服务接口
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
public interface GatewayApplicationService extends IService<GatewayApplicationDO> {
    
    /**
     * 分页查询
     *
     * @param gatewayApplication 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<GatewayApplicationDO> paginQuery(GatewayApplicationDO gatewayApplication, long current, long size);
}