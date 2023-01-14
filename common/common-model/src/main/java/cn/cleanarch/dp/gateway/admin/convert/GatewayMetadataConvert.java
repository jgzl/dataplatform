package cn.cleanarch.dp.gateway.admin.convert;

import cn.cleanarch.dp.common.model.BaseConvert;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayMetadataDO;
import cn.cleanarch.dp.gateway.admin.vo.GatewayMetadataVO;
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
