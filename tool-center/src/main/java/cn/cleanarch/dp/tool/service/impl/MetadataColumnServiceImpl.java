package cn.cleanarch.dp.tool.service.impl;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.core.exception.DataException;
import cn.cleanarch.dp.common.oauth.util.AppContextHolder;
import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.metadata.dto.MetadataColumnDto;
import cn.cleanarch.dp.metadata.entity.MetadataAuthorizeEntity;
import cn.cleanarch.dp.metadata.entity.MetadataColumnEntity;
import cn.cleanarch.dp.metadata.entity.MetadataSourceEntity;
import cn.cleanarch.dp.metadata.entity.MetadataTableEntity;
import cn.cleanarch.dp.metadata.enums.DataLevel;
import cn.cleanarch.dp.metadata.query.MetadataColumnQuery;
import cn.cleanarch.dp.metadata.vo.MetadataTreeVo;
import cn.cleanarch.dp.tool.constants.RedisConstant;
import cn.cleanarch.dp.tool.convert.MetadataColumnConvert;
import cn.cleanarch.dp.tool.mapper.MetadataColumnMapper;
import cn.cleanarch.dp.tool.service.MetadataColumnService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 元数据信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MetadataColumnServiceImpl extends ServiceImpl<MetadataColumnMapper, MetadataColumnEntity> implements MetadataColumnService {

    @Autowired
    private MetadataColumnMapper metadataColumnMapper;

    @Autowired
    private MetadataColumnConvert metadataColumnConvert;

    @Autowired
    private RedisHelper redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataColumnEntity saveMetadataColumn(MetadataColumnDto metadataColumnDto) {
        MetadataColumnEntity metadataColumn = metadataColumnConvert.toEntity(metadataColumnDto);
        metadataColumnMapper.insert(metadataColumn);
        return metadataColumn;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataColumnEntity updateMetadataColumn(MetadataColumnDto metadataColumnDto) {
        MetadataColumnEntity metadataColumn = metadataColumnConvert.toEntity(metadataColumnDto);
        metadataColumnMapper.updateById(metadataColumn);
        return metadataColumn;
    }

    @Override
    public MetadataColumnEntity getMetadataColumnById(String id) {
        MetadataColumnEntity metadataColumnEntity = super.getById(id);
        return metadataColumnEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataColumnById(String id) {
        metadataColumnMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataColumnBatch(List<String> ids) {
        metadataColumnMapper.deleteBatchIds(ids);
    }

    @Override
    public List<MetadataTreeVo> getDataMetadataTree(String level, MetadataColumnQuery metadataColumnQuery) {
        boolean admin = AppContextHolder.isAdmin();
        List<MetadataSourceEntity> sourceList = redisService.objectGet(RedisConstant.METADATA_SOURCE_KEY, new TypeReference<List<MetadataSourceEntity>>() {});
        Stream<MetadataSourceEntity> stream = Optional.ofNullable(sourceList).orElseGet(ArrayList::new).stream().filter(s -> CommonConstants.STATUS_NORMAL.equals(s.getDeleted()));
        if (StrUtil.isNotBlank(metadataColumnQuery.getSourceId())) {
            stream = stream.filter(s -> metadataColumnQuery.getSourceId().equals(s.getId()));
        }
        if (!admin) {
            Set<String> set = new HashSet<>();
            List<String> roleIds = AppContextHolder.getRoles();
            roleIds.stream().forEach(role -> {
                List<MetadataAuthorizeEntity> list = redisService.hshGet(RedisConstant.METADATA_AUTHORIZE_KEY, role, new TypeReference<List<MetadataAuthorizeEntity>>() {});
                set.addAll(Optional.ofNullable(list).orElseGet(ArrayList::new).stream()
                        .filter(s -> Objects.equals(DataLevel.DATABASE.getKey(), s.getObjectType()))
                        .map(s -> s.getObjectId()).collect(Collectors.toSet()));
            });
            stream = stream.filter(s -> set.contains(s.getId()));
        }
        List<MetadataTreeVo> list = stream.map(m -> {
            MetadataTreeVo tree = new MetadataTreeVo();
            tree.setId(m.getId());
            tree.setType(DataLevel.DATABASE.getKey());
            tree.setLabel(m.getSourceName());
            tree.setName(m.getSourceName());
            if (DataLevel.getLevel(level).getLevel() >= DataLevel.TABLE.getLevel()) {
                tree.setChildren(getTableChildrens(m.getId(), level, metadataColumnQuery.getTableId()));
            }
            return tree;
        }).collect(Collectors.toList());
        return list;
    }

    private List<MetadataTreeVo> getTableChildrens(String id, String level, String tableId) {
        boolean admin = AppContextHolder.isAdmin();
        List<MetadataTableEntity> tableList = redisService.hshGet(RedisConstant.METADATA_TABLE_KEY, id, new TypeReference<List<MetadataTableEntity>>() {});
        Stream<MetadataTableEntity> stream = Optional.ofNullable(tableList).orElseGet(ArrayList::new).stream();
        if (StrUtil.isNotBlank(tableId)) {
            stream = stream.filter(s -> tableId.equals(s.getId()));
        }
        if (!admin) {
            Set<String> set = new HashSet<>();
            List<String> roleIds = AppContextHolder.getRoles();
            roleIds.forEach(role -> {
                List<MetadataAuthorizeEntity> list = redisService.hshGet(RedisConstant.METADATA_AUTHORIZE_KEY, role, new TypeReference<List<MetadataAuthorizeEntity>>() {});
                set.addAll(Optional.ofNullable(list).orElseGet(ArrayList::new).stream()
                        .filter(s -> Objects.equals(DataLevel.TABLE.getKey(), s.getObjectType()))
                        .map(MetadataAuthorizeEntity::getObjectId).collect(Collectors.toSet()));
            });
            stream = stream.filter(s -> set.contains(s.getId()));
        }
        List<MetadataTreeVo> children = stream.map(m -> {
            MetadataTreeVo tree = new MetadataTreeVo();
            tree.setId(m.getId());
            tree.setType(DataLevel.TABLE.getKey());
            tree.setName(m.getTableComment());
            tree.setCode(m.getTableName());
            tree.setLabel(StrUtil.isBlank(m.getTableComment()) ? m.getTableName() : m.getTableComment());
            if (DataLevel.getLevel(level).getLevel() >= DataLevel.COLUMN.getLevel()) {
                tree.setChildren(getColumnChildrens(m.getId()));
            }
            return tree;
        }).collect(Collectors.toList());
        return children;
    }

    private List<MetadataTreeVo> getColumnChildrens(String id) {
        boolean admin = AppContextHolder.isAdmin();
        List<MetadataColumnEntity> columnList = (List<MetadataColumnEntity>) redisService.hshGet(RedisConstant.METADATA_COLUMN_KEY, id, new TypeReference<List<MetadataColumnEntity>>() {});
        Stream<MetadataColumnEntity> stream = Optional.ofNullable(columnList).orElseGet(ArrayList::new).stream();
        if (!admin) {
            Set<String> set = new HashSet<>();
            List<String> roleIds = AppContextHolder.getRoles();
            roleIds.forEach(role -> {
                List<MetadataAuthorizeEntity> list = (List<MetadataAuthorizeEntity>) redisService.hshGet(RedisConstant.METADATA_AUTHORIZE_KEY, role, new TypeReference<List<MetadataAuthorizeEntity>>() {});
                set.addAll(Optional.ofNullable(list).orElseGet(ArrayList::new).stream()
                        .filter(s -> Objects.equals(DataLevel.COLUMN.getKey(), s.getObjectType()))
                        .map(MetadataAuthorizeEntity::getObjectId).collect(Collectors.toSet()));
            });
            stream = stream.filter(s -> set.contains(s.getId()));
        }
        List<MetadataTreeVo> children = stream.map(m -> {
            MetadataTreeVo tree = new MetadataTreeVo();
            tree.setId(m.getId());
            tree.setType(DataLevel.COLUMN.getKey());
            tree.setName(m.getColumnComment());
            tree.setCode(m.getColumnName());
            tree.setLabel(StrUtil.isBlank(m.getColumnComment()) ? m.getColumnName() : m.getColumnComment());
            return tree;
        }).collect(Collectors.toList());
        return children;
    }

    @Override
    public List<MetadataColumnEntity> getDataMetadataColumnList(MetadataColumnQuery metadataColumnQuery) {
        boolean admin = AppContextHolder.isAdmin();
        if (StrUtil.isBlank(metadataColumnQuery.getTableId())) {
            throw new DataException("数据表不能为空");
        }
        List<MetadataColumnEntity> columnList = redisService.hshGet(RedisConstant.METADATA_COLUMN_KEY, metadataColumnQuery.getTableId(), new TypeReference<List<MetadataColumnEntity>>() {});
        Stream<MetadataColumnEntity> stream = Optional.ofNullable(columnList).orElseGet(ArrayList::new).stream();
        if (!admin) {
            Set<String> set = new HashSet<>();
            List<String> roleIds = AppContextHolder.getRoles();
            roleIds.forEach(role -> {
                List<MetadataAuthorizeEntity> list = redisService.hshGet(RedisConstant.METADATA_AUTHORIZE_KEY, role, new TypeReference<List<MetadataAuthorizeEntity>>() {});
                set.addAll(Optional.ofNullable(list).orElseGet(ArrayList::new).stream()
                        .filter(s -> Objects.equals(DataLevel.COLUMN.getKey(), s.getObjectType()))
                        .map(MetadataAuthorizeEntity::getObjectId).collect(Collectors.toSet()));
            });
            stream = stream.filter(s -> set.contains(s.getId()));
        }
        return stream.collect(Collectors.toList());
    }

    @Override
    public <E extends IPage<MetadataColumnEntity>> E pageWithAuth(E page, Wrapper<MetadataColumnEntity> queryWrapper) {
        boolean admin = AppContextHolder.isAdmin();
        List<String> roles = new ArrayList<>();
        if (!admin) {
            roles = AppContextHolder.getRoles();
        }
        return metadataColumnMapper.selectPageWithAuth(page, queryWrapper, roles);
    }
}
