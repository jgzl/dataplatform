package cn.cleanarch.dp.gateway.convert;

import cn.cleanarch.dp.common.model.BaseConvert;
import cn.cleanarch.dp.gateway.dataobject.GatewayMetadataDO;
import cn.cleanarch.dp.gateway.vo.GatewayMetadataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Mapper
public interface GatewayMetadataConvert extends BaseConvert<GatewayMetadataVO, GatewayMetadataDO> {

    GatewayMetadataConvert INSTANCE = Mappers.getMapper(GatewayMetadataConvert.class);

    List<GatewayMetadataVO> convertDO2VOList(List<GatewayMetadataDO> list);

    List<GatewayMetadataDO> convertVO2DOList(List<GatewayMetadataVO> list);
}
