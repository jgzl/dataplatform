package cn.cleanarch.dp.tool.convert;

import cn.cleanarch.dp.common.model.EntityMapper;
import cn.cleanarch.dp.tool.metadata.dto.MetadataColumnDto;
import cn.cleanarch.dp.tool.metadata.entity.MetadataColumnEntity;
import cn.cleanarch.dp.tool.metadata.vo.MetadataColumnVo;
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
public interface MetadataColumnConvert extends EntityMapper<MetadataColumnDto, MetadataColumnEntity, MetadataColumnVo> {

}
