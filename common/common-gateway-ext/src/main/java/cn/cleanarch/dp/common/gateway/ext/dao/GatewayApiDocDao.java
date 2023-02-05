package cn.cleanarch.dp.common.gateway.ext.dao;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayApiDocDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description 网关路由API接口文档数据层操作接口
 * @Author JL
 * @Date 2020/11/25
 * @Version V1.0
 */
public interface GatewayApiDocDao extends JpaRepository<GatewayApiDocDO, String> {

}
