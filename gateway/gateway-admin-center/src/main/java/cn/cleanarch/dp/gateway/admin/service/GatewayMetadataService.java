package cn.cleanarch.dp.gateway.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.cleanarch.dp.gateway.dataobject.GatewayMetadataDO;

 /**
 * 网关管理-元数据管理;(GW_GATEWAY_METADATA)表服务接口
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
public interface GatewayMetadataService extends IService<GatewayMetadataDO> {
    
    /**
     * 分页查询
     *
     * @param gatewayMetadata 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<GatewayMetadataDO> paginQuery(GatewayMetadataDO gatewayMetadata, long current, long size);
}