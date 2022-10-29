package cn.cleanarch.dp.tool.service;

import cn.cleanarch.dp.common.database.DbQuery;
import cn.cleanarch.dp.common.database.core.DbColumn;
import cn.cleanarch.dp.common.database.core.DbTable;
import cn.cleanarch.dp.metadata.dto.MetadataSourceDto;
import cn.cleanarch.dp.metadata.entity.MetadataSourceEntity;
import com.aspose.words.Document;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 数据源信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-03-14
 */
public interface MetadataSourceService extends IService<MetadataSourceEntity> {

    void saveMetadataSource(MetadataSourceDto metadataSourceDto);

    void updateMetadataSource(MetadataSourceDto metadataSourceDto);

    MetadataSourceEntity getMetadataSourceById(String id);

    void deleteMetadataSourceById(String id);

    void deleteMetadataSourceBatch(List<String> ids);

    DbQuery checkConnection(MetadataSourceDto metadataSourceDto);

    DbQuery getDbQuery(String id);

    List<DbTable> getDbTables(String id);

    List<DbColumn> getDbTableColumns(String id, String tableName);

    void syncMetadata(String id);

    Document wordMetadata(String id) throws Exception;

    void refreshMetadata();

    List<MetadataSourceEntity> getMetadataSourceList();

    <E extends IPage<MetadataSourceEntity>> E pageWithAuth(E page, Wrapper<MetadataSourceEntity> queryWrapper);
}
