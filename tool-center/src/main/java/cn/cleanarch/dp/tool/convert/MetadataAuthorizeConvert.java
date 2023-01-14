package cn.cleanarch.dp.tool.convert;

import cn.cleanarch.dp.common.model.EntityMapper;
import cn.cleanarch.dp.tool.metadata.dto.MetadataAuthorizeDto;
import cn.cleanarch.dp.tool.metadata.entity.MetadataAuthorizeEntity;
import cn.cleanarch.dp.tool.metadata.vo.MetadataAuthorizeVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 数据授权信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-10-23
 */
@Mapper(componentModel = "spring")
public interface MetadataAuthorizeConvert extends EntityMapper<MetadataAuthorizeDto, MetadataAuthorizeEntity, MetadataAuthorizeVo> {

}
