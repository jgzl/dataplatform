package cn.cleanarch.dp.tool.service.impl;

import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.metadata.dto.MetadataAuthorizeDto;
import cn.cleanarch.dp.metadata.entity.MetadataAuthorizeEntity;
import cn.cleanarch.dp.tool.constants.RedisConstant;
import cn.cleanarch.dp.tool.mapper.MetadataAuthorizeDao;
import cn.cleanarch.dp.tool.mapstruct.MetadataAuthorizeMapper;
import cn.cleanarch.dp.tool.service.MetadataAuthorizeService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据授权信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-10-23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MetadataAuthorizeServiceImpl extends ServiceImpl<MetadataAuthorizeDao, MetadataAuthorizeEntity> implements MetadataAuthorizeService {

    @Autowired
    private MetadataAuthorizeDao metadataAuthorizeDao;

    @Autowired
    private MetadataAuthorizeMapper metadataAuthorizeMapper;

    @Autowired
    private RedisHelper redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<String> getAuthorizedMetadata(String id) {
        List<MetadataAuthorizeEntity> metadataAuthorizeList = metadataAuthorizeDao.selectList(Wrappers.<MetadataAuthorizeEntity>lambdaQuery().eq(MetadataAuthorizeEntity::getRoleId, id));
        List<String> list = metadataAuthorizeList.stream().map(s -> s.getObjectId()).collect(Collectors.toList());
        return list;
    }

    @Override
    public void metadataAuthorize(MetadataAuthorizeDto metadataAuthorizeDto) {
        // 先删除
        metadataAuthorizeDao.delete(Wrappers.<MetadataAuthorizeEntity>lambdaQuery().eq(MetadataAuthorizeEntity::getRoleId, metadataAuthorizeDto.getRoleId()));
        metadataAuthorizeDto.getAuthorizeDataList().stream().forEach(s -> {
            MetadataAuthorizeEntity metadataAuthorizeEntity = new MetadataAuthorizeEntity();
            metadataAuthorizeEntity.setRoleId(s.getRoleId());
            metadataAuthorizeEntity.setObjectId(s.getObjectId());
            metadataAuthorizeEntity.setObjectType(s.getObjectType());
            metadataAuthorizeDao.insert(metadataAuthorizeEntity);
        });
    }

    @Override
    public void refreshCache() {
        String authorizeKey = RedisConstant.METADATA_AUTHORIZE_KEY;
        Boolean hasAuthorizeKey = redisService.hasKey(authorizeKey);
        if (hasAuthorizeKey) {
            redisService.delKey(authorizeKey);
        }
        List<MetadataAuthorizeEntity> metadataAuthorizeList = metadataAuthorizeDao.selectList(Wrappers.emptyWrapper());
        Map<String, List<MetadataAuthorizeEntity>> authorizeListMap = metadataAuthorizeList.stream().collect(Collectors.groupingBy(MetadataAuthorizeEntity::getRoleId));
        redisTemplate.opsForHash().putAll(authorizeKey, authorizeListMap);
    }
}
