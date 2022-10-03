package cn.cleanarch.dp.tool.service.impl;

import cn.cleanarch.dp.metadata.dto.MetadataChangeRecordDto;
import cn.cleanarch.dp.metadata.entity.MetadataChangeRecordEntity;
import cn.cleanarch.dp.tool.mapper.MetadataChangeRecordDao;
import cn.cleanarch.dp.tool.mapstruct.MetadataChangeRecordMapper;
import cn.cleanarch.dp.tool.service.MetadataChangeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 元数据变更记录表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-30
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MetadataChangeRecordServiceImpl extends ServiceImpl<MetadataChangeRecordDao, MetadataChangeRecordEntity> implements MetadataChangeRecordService {

    @Autowired
    private MetadataChangeRecordDao metadataChangeRecordDao;

    @Autowired
    private MetadataChangeRecordMapper metadataChangeRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataChangeRecordEntity saveMetadataChangeRecord(MetadataChangeRecordDto metadataChangeRecordDto) {
        MetadataChangeRecordEntity metadataChangeRecord = metadataChangeRecordMapper.toEntity(metadataChangeRecordDto);
        metadataChangeRecordDao.insert(metadataChangeRecord);
        return metadataChangeRecord;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataChangeRecordEntity updateMetadataChangeRecord(MetadataChangeRecordDto metadataChangeRecordDto) {
        MetadataChangeRecordEntity metadataChangeRecord = metadataChangeRecordMapper.toEntity(metadataChangeRecordDto);
        metadataChangeRecordDao.updateById(metadataChangeRecord);
        return metadataChangeRecord;
    }

    @Override
    public MetadataChangeRecordEntity getMetadataChangeRecordById(String id) {
        MetadataChangeRecordEntity metadataChangeRecordEntity = super.getById(id);
        return metadataChangeRecordEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataChangeRecordById(String id) {
        metadataChangeRecordDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataChangeRecordBatch(List<String> ids) {
        metadataChangeRecordDao.deleteBatchIds(ids);
    }
}
