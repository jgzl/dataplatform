package cn.cleanarch.gw.gateway.configuration;

import cn.cleanarch.gw.common.core.constant.CacheConstants;
import cn.cleanarch.gw.common.gateway.support.GatewayAccessConfCacheHolder;
import cn.cleanarch.gw.common.model.gateway.vo.GatewayAccessConfVo;
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
        List<GatewayAccessConfVo> vos = redisTemplate.<String, GatewayAccessConfVo>opsForHash().values(CacheConstants.ACCESS_CONF_KEY);
        if (CollUtil.isEmpty(vos)) {
            vos = ListUtil.empty();
        }
        GatewayAccessConfCacheHolder.setList(vos);
        log.info("结束预热Redis缓存数据至本地缓存");
    }
}
