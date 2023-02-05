package cn.cleanarch.dp.common.gateway.ext.dao;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewaySecureIpDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description IP管理Dao数据层操作接口
 * @Author jianglong
 * @Date 2020/05/28
 * @Version V1.0
 */
public interface GatewaySecureIpDao extends JpaRepository<GatewaySecureIpDO, String> {
}
