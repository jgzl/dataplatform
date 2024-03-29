package cn.cleanarch.dp.gateway.configuration;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.common.gateway.support.GatewayAccessCacheHolder;
import cn.cleanarch.dp.gateway.admin.vo.GatewayAccessConfVO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author lihaifeng
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class GatewayAccessConfCacheConfiguration implements InitializingBean {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("开始预热Redis缓存数据至本地缓存");
        List<GatewayAccessConfVO> vos = redisTemplate.<String, GatewayAccessConfVO>opsForHash().values(CacheConstants.ACCESS_KEY);
        if (CollUtil.isEmpty(vos)) {
            vos = ListUtil.empty();
        }
        GatewayAccessCacheHolder.setList(vos);
        log.info("结束预热Redis缓存数据至本地缓存");
    }
}
