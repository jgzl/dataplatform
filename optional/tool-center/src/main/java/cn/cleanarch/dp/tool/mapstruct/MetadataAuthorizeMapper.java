package cn.cleanarch.dp.tool.mapstruct;

import cn.cleanarch.dp.common.EntityMapper;
import cn.cleanarch.dp.metadata.dto.MetadataAuthorizeDto;
import cn.cleanarch.dp.metadata.entity.MetadataAuthorizeEntity;
import cn.cleanarch.dp.metadata.vo.MetadataAuthorizeVo;
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
public interface MetadataAuthorizeMapper extends EntityMapper<MetadataAuthorizeDto, MetadataAuthorizeEntity, MetadataAuthorizeVo> {

}
