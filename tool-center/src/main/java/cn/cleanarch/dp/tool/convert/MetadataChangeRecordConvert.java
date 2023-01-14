package cn.cleanarch.dp.tool.convert;

import cn.cleanarch.dp.common.model.EntityMapper;
import cn.cleanarch.dp.tool.metadata.dto.MetadataChangeRecordDto;
import cn.cleanarch.dp.tool.metadata.entity.MetadataChangeRecordEntity;
import cn.cleanarch.dp.tool.metadata.vo.MetadataChangeRecordVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 元数据变更记录表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-07-30
 */
@Mapper(componentModel = "spring")
public interface MetadataChangeRecordConvert extends EntityMapper<MetadataChangeRecordDto, MetadataChangeRecordEntity, MetadataChangeRecordVo> {

}
