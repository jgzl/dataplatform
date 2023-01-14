package cn.cleanarch.dp.tool.service;

import cn.cleanarch.dp.tool.metadata.dto.MetadataChangeRecordDto;
import cn.cleanarch.dp.tool.metadata.entity.MetadataChangeRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 元数据变更记录表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-30
 */
public interface MetadataChangeRecordService extends IService<MetadataChangeRecordEntity> {

    MetadataChangeRecordEntity saveMetadataChangeRecord(MetadataChangeRecordDto metadataChangeRecord);

    MetadataChangeRecordEntity updateMetadataChangeRecord(MetadataChangeRecordDto metadataChangeRecord);

    MetadataChangeRecordEntity getMetadataChangeRecordById(String id);

    void deleteMetadataChangeRecordById(String id);

    void deleteMetadataChangeRecordBatch(List<String> ids);
}
