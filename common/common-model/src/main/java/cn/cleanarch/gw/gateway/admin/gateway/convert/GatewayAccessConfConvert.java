package cn.cleanarch.gw.gateway.admin.gateway.convert;

import cn.cleanarch.gw.common.BaseConvert;
import cn.cleanarch.gw.gateway.admin.gateway.domain.GatewayAccessConfDO;
import cn.cleanarch.gw.gateway.admin.gateway.vo.GatewayAccessConfVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Mapper
public interface GatewayAccessConfConvert extends BaseConvert<GatewayAccessConfVO, GatewayAccessConfDO> {
    GatewayAccessConfConvert INSTANCE = Mappers.getMapper(GatewayAccessConfConvert.class);
}
