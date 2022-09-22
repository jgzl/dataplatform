package cn.cleanarch.gw.common.gateway.convert;

import cn.cleanarch.gw.common.BaseConvert;
import cn.cleanarch.gw.common.gateway.domain.GatewayLog;
import cn.cleanarch.gw.common.gateway.vo.GatewayLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GatewayLogConvert extends BaseConvert<GatewayLogVo, GatewayLog> {
    GatewayLogConvert INSTANCE = Mappers.getMapper(GatewayLogConvert.class);
}
