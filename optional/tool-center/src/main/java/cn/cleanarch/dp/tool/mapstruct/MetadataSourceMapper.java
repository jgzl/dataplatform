package cn.cleanarch.dp.tool.mapstruct;

import cn.cleanarch.dp.common.EntityMapper;
import cn.cleanarch.dp.metadata.dto.MetadataSourceDto;
import cn.cleanarch.dp.metadata.entity.MetadataSourceEntity;
import cn.cleanarch.dp.metadata.vo.MetadataSourceVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 数据源信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-03-14
 */
@Mapper(componentModel = "spring")
public interface MetadataSourceMapper extends EntityMapper<MetadataSourceDto, MetadataSourceEntity, MetadataSourceVo> {

}
