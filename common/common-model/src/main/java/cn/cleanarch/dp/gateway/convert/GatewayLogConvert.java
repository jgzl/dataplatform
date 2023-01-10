package cn.cleanarch.dp.gateway.convert;

import cn.cleanarch.dp.common.model.BaseConvert;
import cn.cleanarch.dp.gateway.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.vo.GatewayLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GatewayLogConvert extends BaseConvert<GatewayLogVO, GatewayLogDO> {
    GatewayLogConvert INSTANCE = Mappers.getMapper(GatewayLogConvert.class);
}
