package cn.cleanarch.gw.common.gateway.convert;

import cn.cleanarch.gw.common.BaseConvert;
import cn.cleanarch.gw.common.gateway.vo.GatewayRouteDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

@Mapper
public interface GatewayRouteDefinitionConvert extends BaseConvert<GatewayRouteDefinition, RouteDefinition> {
    GatewayRouteDefinitionConvert INSTANCE = Mappers.getMapper(GatewayRouteDefinitionConvert.class);

    List<GatewayRouteDefinition> convert2VoList(List<RouteDefinition> model);

    List<RouteDefinition> convert2DoList(List<GatewayRouteDefinition> model);
}