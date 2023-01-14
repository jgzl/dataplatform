package cn.cleanarch.dp.gateway.admin.mapper;

import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 网关访问
 *
 * @author li7hai26@gmail.com
 * @date 2018-11-06 10:17:18
 */
@Mapper
public interface GatewayLogMapper extends BaseMapper<GatewayLogDO> {

}
