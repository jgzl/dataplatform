package cn.cleanarch.dp.common.gateway.convert;

import cn.cleanarch.dp.common.model.BaseConvert;
import cn.cleanarch.dp.gateway.admin.vo.GatewayRouteDefinitionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

@Mapper
public interface GatewayRouteDefinitionConvert extends BaseConvert<GatewayRouteDefinitionVO, RouteDefinition> {
    GatewayRouteDefinitionConvert INSTANCE = Mappers.getMapper(GatewayRouteDefinitionConvert.class);

    List<GatewayRouteDefinitionVO> convert2VoList(List<RouteDefinition> model);

    List<RouteDefinition> convert2DoList(List<GatewayRouteDefinitionVO> model);
}