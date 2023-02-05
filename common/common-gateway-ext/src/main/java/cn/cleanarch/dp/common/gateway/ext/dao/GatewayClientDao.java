package cn.cleanarch.dp.common.gateway.ext.dao;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayClientDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description 客户端Dao数据层操作接口
 * @Author jianglong
 * @Date 2020/05/15
 * @Version V1.0
 */
public interface GatewayClientDao extends JpaRepository<GatewayClientDO, String> {

}
