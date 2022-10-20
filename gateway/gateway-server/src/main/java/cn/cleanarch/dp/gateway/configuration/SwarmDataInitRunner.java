package cn.cleanarch.dp.gateway.configuration;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.common.gateway.support.GatewayAccessCacheHolder;
import cn.cleanarch.dp.gateway.vo.GatewayAccessVO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author lihaifeng
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class SwarmDataInitRunner implements CommandLineRunner{

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始预热Redis缓存数据至本地缓存");
        List<GatewayAccessVO> vos = redisTemplate.<String, GatewayAccessVO>opsForHash().values(CacheConstants.ACCESS_CONF_KEY);
        if (CollUtil.isEmpty(vos)) {
            vos = ListUtil.empty();
        }
        GatewayAccessCacheHolder.setList(vos);
        log.info("结束预热Redis缓存数据至本地缓存");
    }
}
