package cn.cleanarch.dp.tool.service.impl;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.core.exception.DataException;
import cn.cleanarch.dp.common.database.DataSourceFactory;
import cn.cleanarch.dp.common.database.DbQuery;
import cn.cleanarch.dp.common.database.constants.DbQueryProperty;
import cn.cleanarch.dp.common.database.core.DbColumn;
import cn.cleanarch.dp.common.database.core.DbTable;
import cn.cleanarch.dp.common.oauth.util.AppContextHolder;
import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.metadata.dto.DbSchema;
import cn.cleanarch.dp.metadata.dto.MetadataSourceDto;
import cn.cleanarch.dp.metadata.entity.MetadataAuthorizeEntity;
import cn.cleanarch.dp.metadata.entity.MetadataColumnEntity;
import cn.cleanarch.dp.metadata.entity.MetadataSourceEntity;
import cn.cleanarch.dp.metadata.entity.MetadataTableEntity;
import cn.cleanarch.dp.metadata.enums.DataLevel;
import cn.cleanarch.dp.metadata.enums.SyncStatus;
import cn.cleanarch.dp.tool.async.AsyncTask;
import cn.cleanarch.dp.tool.constants.RedisConstant;
import cn.cleanarch.dp.tool.convert.MetadataSourceConvert;
import cn.cleanarch.dp.tool.mapper.MetadataColumnMapper;
import cn.cleanarch.dp.tool.mapper.MetadataSourceMapper;
import cn.cleanarch.dp.tool.mapper.MetadataTableMapper;
import cn.cleanarch.dp.tool.service.MetadataSourceService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 数据源信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-03-14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MetadataSourceServiceImpl extends ServiceImpl<MetadataSourceMapper, MetadataSourceEntity> implements MetadataSourceService {

    @Autowired
    private MetadataSourceMapper metadataSourceMapper;

    @Autowired
    private MetadataSourceConvert metadataSourceConvert;

    @Autowired
    private DataSourceFactory dataSourceFactory;

    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private MetadataTableMapper metadataTableDao;

    @Autowired
    private MetadataColumnMapper metadataColumnMapper;

    @Autowired
    private RedisHelper redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMetadataSource(MetadataSourceDto metadataSourceDto) {
        MetadataSourceEntity dataSource = metadataSourceConvert.toEntity(metadataSourceDto);
        dataSource.setIsSync(SyncStatus.NotSync.getKey());
        metadataSourceMapper.insert(dataSource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMetadataSource(MetadataSourceDto metadataSourceDto) {
        MetadataSourceEntity dataSource = metadataSourceConvert.toEntity(metadataSourceDto);
        metadataSourceMapper.updateById(dataSource);
    }

    @Override
    public MetadataSourceEntity getMetadataSourceById(String id) {
        MetadataSourceEntity metadataSourceEntity = super.getById(id);
        return metadataSourceEntity;
    }

    @Override
    public List<MetadataSourceEntity> getMetadataSourceList() {
        boolean admin = AppContextHolder.isAdmin();
        List<MetadataSourceEntity> sourceList = redisService.objectGet(RedisConstant.METADATA_SOURCE_KEY, new TypeReference<List<MetadataSourceEntity>>() {});
        Stream<MetadataSourceEntity> stream = Optional.ofNullable(sourceList).orElseGet(ArrayList::new).stream()
                .filter(s -> CommonConstants.STATUS_NORMAL.equals(s.getDeleted()));
        if (!admin) {
            Set<String> set = new HashSet<>();
            List<String> roleIds = AppContextHolder.getRoles();
            roleIds.forEach(role -> {
                List<MetadataAuthorizeEntity> list = redisService.hshGet(RedisConstant.METADATA_AUTHORIZE_KEY, role, new TypeReference<List<MetadataAuthorizeEntity>>() {});
                set.addAll(Optional.ofNullable(list).orElseGet(ArrayList::new).stream()
                        .filter(s -> Objects.equals(DataLevel.DATABASE.getKey(), s.getObjectType()))
                        .map(MetadataAuthorizeEntity::getObjectId).collect(Collectors.toSet()));
            });
            stream = stream.filter(s -> set.contains(s.getId()));
        }
        return stream.collect(Collectors.toList());
    }

    @Override
    public <E extends IPage<MetadataSourceEntity>> E pageWithAuth(E page, Wrapper<MetadataSourceEntity> queryWrapper) {
        boolean admin = AppContextHolder.isAdmin();
        List<String> roles = new ArrayList<>();
        if (!admin) {
            roles = AppContextHolder.getRoles();
        }
        return metadataSourceMapper.selectPageWithAuth(page, queryWrapper, roles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataSourceById(String id) {
        metadataSourceMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataSourceBatch(List<String> ids) {
        metadataSourceMapper.deleteBatchIds(ids);
    }

    @Override
    public DbQuery checkConnection(MetadataSourceDto metadataSourceDto) {
        MetadataSourceEntity dataSource = metadataSourceConvert.toEntity(metadataSourceDto);
        DbSchema dbSchema = dataSource.getDbSchema();
        DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
        DbQuery dbQuery = dataSourceFactory.createDbQuery(dbQueryProperty);
        return dbQuery;
    }

    @Override
    public DbQuery getDbQuery(String id) {
        MetadataSourceEntity dataSource = super.getById(id);
        DbSchema dbSchema = dataSource.getDbSchema();
        DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
        DbQuery dbQuery = dataSourceFactory.createDbQuery(dbQueryProperty);
        return dbQuery;
    }

    @Override
    public List<DbTable> getDbTables(String id) {
        MetadataSourceEntity dataSource = super.getById(id);
        DbSchema dbSchema = dataSource.getDbSchema();
        DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
        DbQuery dbQuery = dataSourceFactory.createDbQuery(dbQueryProperty);
        List<DbTable> tables = dbQuery.getTables(dbSchema.getDbName());
        return tables;
    }

    @Override
    public List<DbColumn> getDbTableColumns(String id, String tableName) {
        MetadataSourceEntity dataSource = super.getById(id);
        DbSchema dbSchema = dataSource.getDbSchema();
        DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
        DbQuery dbQuery = dataSourceFactory.createDbQuery(dbQueryProperty);
        List<DbColumn> columns = dbQuery.getTableColumns(dbSchema.getDbName(), tableName);
        return columns;
    }

    @Override
    public void syncMetadata(String id) {
        MetadataSourceEntity metadataSourceEntity = super.getById(id);
        if (!SyncStatus.NotSync.getKey().equals(metadataSourceEntity.getIsSync())) {
            throw new DataException("元数据同步中");
        }
        metadataSourceEntity.setIsSync(SyncStatus.InSync.getKey());
        metadataSourceMapper.updateById(metadataSourceEntity);
        // 异步执行同步任务
        asyncTask.doTask(metadataSourceEntity);
    }

    @Override
    public void refreshMetadata() {
        String sourceKey = RedisConstant.METADATA_SOURCE_KEY;
        Boolean hasSourceKey = redisService.hasKey(sourceKey);
        if (hasSourceKey) {
            redisService.delKey(sourceKey);
        }
        List<MetadataSourceEntity> sourceEntityList = metadataSourceMapper.selectList(Wrappers.emptyWrapper());
        redisService.objectSet(sourceKey, sourceEntityList);

        String tableKey = RedisConstant.METADATA_TABLE_KEY;
        Boolean hasTableKey = redisService.hasKey(tableKey);
        if (hasTableKey) {
            redisService.delKey(tableKey);
        }
        List<MetadataTableEntity> tableEntityList = metadataTableDao.selectList(Wrappers.emptyWrapper());
        Map<String, List<MetadataTableEntity>> tableListMap = tableEntityList.stream().collect(Collectors.groupingBy(MetadataTableEntity::getSourceId));
        redisTemplate.opsForHash().putAll(tableKey, tableListMap);

        String columnKey = RedisConstant.METADATA_COLUMN_KEY;
        Boolean hasColumnKey = redisService.hasKey(columnKey);
        if (hasColumnKey) {
            redisService.delKey(columnKey);
        }
        List<MetadataColumnEntity> columnEntityList = metadataColumnMapper.selectList(Wrappers.emptyWrapper());
        Map<String, List<MetadataColumnEntity>> columnListMap = columnEntityList.stream().collect(Collectors.groupingBy(MetadataColumnEntity::getTableId));
        redisTemplate.opsForHash().putAll(columnKey, columnListMap);
    }
}
