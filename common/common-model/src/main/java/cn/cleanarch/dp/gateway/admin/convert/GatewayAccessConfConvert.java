package cn.cleanarch.dp.gateway.admin.convert;

import cn.cleanarch.dp.common.model.BaseConvert;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayAccessDO;
import cn.cleanarch.dp.gateway.admin.vo.GatewayAccessVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Mapper
public interface GatewayAccessConfConvert extends BaseConvert<GatewayAccessVO, GatewayAccessDO> {
    GatewayAccessConfConvert INSTANCE = Mappers.getMapper(GatewayAccessConfConvert.class);
}
