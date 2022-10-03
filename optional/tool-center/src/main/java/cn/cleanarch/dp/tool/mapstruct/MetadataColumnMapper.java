package cn.cleanarch.dp.tool.mapstruct;

import cn.cleanarch.dp.common.EntityMapper;
import cn.cleanarch.dp.metadata.dto.MetadataColumnDto;
import cn.cleanarch.dp.metadata.entity.MetadataColumnEntity;
import cn.cleanarch.dp.metadata.vo.MetadataColumnVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 元数据信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Mapper(componentModel = "spring")
public interface MetadataColumnMapper extends EntityMapper<MetadataColumnDto, MetadataColumnEntity, MetadataColumnVo> {

}
