package cn.cleanarch.dp.gateway.convert;

import cn.cleanarch.dp.common.model.BaseConvert;
import cn.cleanarch.dp.gateway.domain.GatewayAccessConfDO;
import cn.cleanarch.dp.gateway.vo.GatewayAccessConfVO;
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
