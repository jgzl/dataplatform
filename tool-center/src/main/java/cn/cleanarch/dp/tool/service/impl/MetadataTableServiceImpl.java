package cn.cleanarch.dp.tool.service.impl;

import cn.cleanarch.dp.common.core.exception.DataException;
import cn.cleanarch.dp.common.oauth.util.AppContextHolder;
import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.tool.metadata.dto.MetadataTableDto;
import cn.cleanarch.dp.tool.metadata.entity.MetadataAuthorizeEntity;
import cn.cleanarch.dp.tool.metadata.entity.MetadataTableEntity;
import cn.cleanarch.dp.tool.metadata.enums.DataLevel;
import cn.cleanarch.dp.tool.metadata.query.MetadataTableQuery;
import cn.cleanarch.dp.tool.constants.RedisConstant;
import cn.cleanarch.dp.tool.convert.MetadataTableConvert;
import cn.cleanarch.dp.tool.mapper.MetadataTableMapper;
import cn.cleanarch.dp.tool.service.MetadataTableService;
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
 * 数据库表信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MetadataTableServiceImpl extends ServiceImpl<MetadataTableMapper, MetadataTableEntity> implements MetadataTableService {

    @Autowired
    private MetadataTableMapper metadataTableMapper;

    @Autowired
    private MetadataTableConvert metadataTableConvert;

    @Autowired
    private RedisHelper redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataTableEntity saveMetadataTable(MetadataTableDto metadataTableDto) {
        MetadataTableEntity metadataTableEntity = metadataTableConvert.toEntity(metadataTableDto);
        metadataTableMapper.insert(metadataTableEntity);
        return metadataTableEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataTableEntity updateMetadataTable(MetadataTableDto metadataTableDto) {
        MetadataTableEntity metadataTableEntity = metadataTableConvert.toEntity(metadataTableDto);
        metadataTableMapper.updateById(metadataTableEntity);
        return metadataTableEntity;
    }

    @Override
    public MetadataTableEntity getMetadataTableById(String id) {
        MetadataTableEntity metadataTableEntity = super.getById(id);
        return metadataTableEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataTableById(String id) {
        metadataTableMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataTableBatch(List<String> ids) {
        metadataTableMapper.deleteBatchIds(ids);
    }

    @Override
    public List<MetadataTableEntity> getDataMetadataTableList(MetadataTableQuery metadataTableQuery) {
        boolean admin = AppContextHolder.isAdmin();
        if (StrUtil.isBlank(metadataTableQuery.getSourceId())) {
            throw new DataException("数据源不能为空");
        }
        List<MetadataTableEntity> tableList = redisService.hshGet(RedisConstant.METADATA_TABLE_KEY, metadataTableQuery.getSourceId(), new TypeReference<List<MetadataTableEntity>>() {});
        Stream<MetadataTableEntity> stream = Optional.ofNullable(tableList).orElseGet(ArrayList::new).stream();
        if (!admin) {
            Set<String> set = new HashSet<>();
            List<String> roleIds = AppContextHolder.getRoles();
            roleIds.stream().forEach(role -> {
                List<MetadataAuthorizeEntity> list = redisService.hshGet(RedisConstant.METADATA_AUTHORIZE_KEY, role, new TypeReference<List<MetadataAuthorizeEntity>>() {});
                set.addAll(Optional.ofNullable(list).orElseGet(ArrayList::new).stream()
                        .filter(s -> Objects.equals(DataLevel.TABLE.getKey(), s.getObjectType()))
                        .map(s -> s.getObjectId()).collect(Collectors.toSet()));
            });
            stream = stream.filter(s -> set.contains(s.getId()));
        }
        return stream.collect(Collectors.toList());
    }

    @Override
    public <E extends IPage<MetadataTableEntity>> E pageWithAuth(E page, Wrapper<MetadataTableEntity> queryWrapper) {
        boolean admin = AppContextHolder.isAdmin();
        List<String> roles = new ArrayList<>();
        if (!admin) {
            roles = AppContextHolder.getRoles();
        }
        return metadataTableMapper.selectPageWithAuth(page, queryWrapper, roles);
    }
}
