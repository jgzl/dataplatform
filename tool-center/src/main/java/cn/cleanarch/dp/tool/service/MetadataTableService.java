package cn.cleanarch.dp.tool.service;

import cn.cleanarch.dp.tool.metadata.dto.MetadataTableDto;
import cn.cleanarch.dp.tool.metadata.entity.MetadataTableEntity;
import cn.cleanarch.dp.tool.metadata.query.MetadataTableQuery;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 数据库表信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
public interface MetadataTableService extends IService<MetadataTableEntity> {

    MetadataTableEntity saveMetadataTable(MetadataTableDto metadataTable);

    MetadataTableEntity updateMetadataTable(MetadataTableDto metadataTable);

    MetadataTableEntity getMetadataTableById(String id);

    void deleteMetadataTableById(String id);

    void deleteMetadataTableBatch(List<String> ids);

    List<MetadataTableEntity> getDataMetadataTableList(MetadataTableQuery metadataTableQuery);

    <E extends IPage<MetadataTableEntity>> E pageWithAuth(E page, Wrapper<MetadataTableEntity> queryWrapper);
}
