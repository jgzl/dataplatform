package cn.cleanarch.dp.tool.convert;

import cn.cleanarch.dp.common.model.EntityMapper;
import cn.cleanarch.dp.metadata.dto.MetadataTableDto;
import cn.cleanarch.dp.metadata.entity.MetadataTableEntity;
import cn.cleanarch.dp.metadata.vo.MetadataTableVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 数据库表信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Mapper(componentModel = "spring")
public interface MetadataTableConvert extends EntityMapper<MetadataTableDto, MetadataTableEntity, MetadataTableVo> {

}
