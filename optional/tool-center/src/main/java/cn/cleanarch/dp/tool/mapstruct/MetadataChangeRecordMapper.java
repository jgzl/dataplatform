package cn.cleanarch.dp.tool.mapstruct;

import cn.cleanarch.dp.common.EntityMapper;
import cn.cleanarch.dp.metadata.dto.MetadataChangeRecordDto;
import cn.cleanarch.dp.metadata.entity.MetadataChangeRecordEntity;
import cn.cleanarch.dp.metadata.vo.MetadataChangeRecordVo;
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
public interface MetadataChangeRecordMapper extends EntityMapper<MetadataChangeRecordDto, MetadataChangeRecordEntity, MetadataChangeRecordVo> {

}
