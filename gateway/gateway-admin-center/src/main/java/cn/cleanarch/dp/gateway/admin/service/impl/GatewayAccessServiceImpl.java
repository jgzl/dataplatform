package cn.cleanarch.dp.gateway.admin.service.impl;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.gateway.admin.mapper.GatewayAccessMapper;
import cn.cleanarch.dp.gateway.admin.service.GatewayAccessService;
import cn.cleanarch.dp.gateway.admin.convert.GatewayAccessConfConvert;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayAccessDO;
import cn.cleanarch.dp.gateway.admin.vo.GatewayAccessVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 网关访问
 *
 * @author li7hai26@gmail.com
 * @date 2018年11月06日10:27:55
 */
@Slf4j
@RequiredArgsConstructor
@Service("gatewayAccessConfService")
public class GatewayAccessServiceImpl extends ServiceImpl<GatewayAccessMapper, GatewayAccessDO>
        implements GatewayAccessService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 新增或者更新
     *
     * @param item
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(GatewayAccessDO item) {
        try {
            boolean result = super.saveOrUpdate(item);
            GatewayAccessVO vo = GatewayAccessConfConvert.INSTANCE.convertDo2Vo(item);
            // redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(GatewayAccessConfVo.class));
            redisTemplate.opsForHash().put(CacheConstants.ACCESS_KEY, vo.getApiKey(), vo);
            redisTemplate.convertAndSend(CacheConstants.ACCESS_JVM_RELOAD_TOPIC, "缓存更新");
            return result;
        } catch (Exception e) {
            log.error("更新数据发生异常", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除
     *
     * @param id id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteItem(String id) {
        GatewayAccessDO item = this.getById(id);
        try {
            super.removeById(id);
            redisTemplate.opsForHash().delete(CacheConstants.ACCESS_KEY, item.getApiKey());
            redisTemplate.convertAndSend(CacheConstants.ACCESS_JVM_RELOAD_TOPIC, "缓存更新");
        } catch (Exception e) {
            log.error("更新数据发生异常", e);
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateStatus(GatewayAccessVO vo) {
        vo.setUpdateTime(LocalDateTime.now());
        GatewayAccessDO domain = GatewayAccessConfConvert.INSTANCE.convertVo2Do(vo);
        try {
            boolean result = super.updateById(domain);
            // redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(GatewayAccessConfVo.class));
            redisTemplate.opsForHash().put(CacheConstants.ACCESS_KEY, vo.getApiKey(), vo);
            redisTemplate.convertAndSend(CacheConstants.ACCESS_JVM_RELOAD_TOPIC, "缓存更新");
            return result;
        } catch (Exception e) {
            log.error("更新数据发生异常", e);
            throw new RuntimeException(e);
        }
    }
}
