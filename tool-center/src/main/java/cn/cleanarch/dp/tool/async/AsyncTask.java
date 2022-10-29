package cn.cleanarch.dp.tool.async;

import cn.cleanarch.dp.common.database.DataSourceFactory;
import cn.cleanarch.dp.common.database.DbQuery;
import cn.cleanarch.dp.common.database.constants.DbQueryProperty;
import cn.cleanarch.dp.common.database.core.DbColumn;
import cn.cleanarch.dp.common.database.core.DbTable;
import cn.cleanarch.dp.metadata.dto.DbSchema;
import cn.cleanarch.dp.metadata.entity.MetadataColumnEntity;
import cn.cleanarch.dp.metadata.entity.MetadataSourceEntity;
import cn.cleanarch.dp.metadata.entity.MetadataTableEntity;
import cn.cleanarch.dp.metadata.enums.SyncStatus;
import cn.cleanarch.dp.tool.mapper.MetadataColumnMapper;
import cn.cleanarch.dp.tool.mapper.MetadataSourceMapper;
import cn.cleanarch.dp.tool.mapper.MetadataTableMapper;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 异步处理
 */
@Slf4j
@Component
public class AsyncTask {

    @Autowired
    private DataSourceFactory dataSourceFactory;

    @Autowired
    private MetadataSourceMapper metadataSourceDao;

    @Autowired
    private MetadataTableMapper metadataTableDao;

    @Autowired
    private MetadataColumnMapper metadataColumnDao;

    @Async("taskExecutor")
    public void doTask(MetadataSourceEntity dataSource) {
        long start = System.currentTimeMillis();
        DbSchema dbSchema = dataSource.getDbSchema();
        DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
        DbQuery dbQuery = dataSourceFactory.createDbQuery(dbQueryProperty);
        List<DbTable> tables = dbQuery.getTables(dbSchema.getDbName());
        if (CollUtil.isNotEmpty(tables)) {
            List<MetadataTableEntity> metadataTableEntityList = tables.stream().map(table -> {
                MetadataTableEntity metadataTable = new MetadataTableEntity();
                metadataTable.setSourceId(dataSource.getId()+"");
                metadataTable.setTableName(table.getTableName());
                metadataTable.setTableComment(table.getTableComment());
                return metadataTable;
            }).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(metadataTableEntityList)) {
                metadataTableEntityList.stream().forEach(table -> {
                    metadataTableDao.insert(table);
                    List<DbColumn> columns = dbQuery.getTableColumns(dbSchema.getDbName(), table.getTableName());
                    if (CollUtil.isNotEmpty(columns)) {
                        List<MetadataColumnEntity> metadataColumnEntityList = columns.stream().map(column -> {
                            MetadataColumnEntity metadataColumn = new MetadataColumnEntity();
                            metadataColumn.setSourceId(dataSource.getId()+"");
                            metadataColumn.setTableId(table.getId());
                            metadataColumn.setColumnName(column.getColName());
                            metadataColumn.setColumnComment(column.getColComment());
                            metadataColumn.setColumnKey(column.getColKey() ? "1" : "0");
                            metadataColumn.setColumnNullable(column.getNullable() ? "1" : "0");
                            metadataColumn.setColumnPosition(column.getColPosition().toString());
                            metadataColumn.setDataType(column.getDataType());
                            metadataColumn.setDataLength(column.getDataLength());
                            metadataColumn.setDataPrecision(column.getDataPrecision());
                            metadataColumn.setDataScale(column.getDataScale());
                            metadataColumn.setDataDefault(column.getDataDefault());
                            return metadataColumn;
                        }).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(metadataColumnEntityList)) {
                            metadataColumnEntityList.stream().forEach(column -> metadataColumnDao.insert(column));
                        }
                    }
                });
            }
        }
        dataSource.setIsSync(SyncStatus.IsSync.getKey());
        metadataSourceDao.updateById(dataSource);
        log.info("异步任务执行完成！耗时{}秒", (System.currentTimeMillis() - start  / 1000));
    }
}