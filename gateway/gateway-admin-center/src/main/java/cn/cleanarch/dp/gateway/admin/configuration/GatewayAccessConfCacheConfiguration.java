package cn.cleanarch.dp.gateway.admin.configuration;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.gateway.admin.service.GatewayAccessService;
import cn.cleanarch.dp.gateway.admin.convert.GatewayAccessConfConvert;
import cn.cleanarch.dp.gateway.admin.vo.GatewayAccessVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author lihaifeng
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class GatewayAccessConfCacheConfiguration implements InitializingBean {

    private final GatewayAccessService accessConfService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("开始预热MySQL数据至Redis缓存数据");
        redisTemplate.delete(CacheConstants.ACCESS_KEY);
        accessConfService.list().forEach(item -> {
            GatewayAccessVO vo = GatewayAccessConfConvert.INSTANCE.convertDo2Vo(item);
            redisTemplate.opsForHash().put(CacheConstants.ACCESS_KEY,vo.getApiKey(),vo);
        });
        redisTemplate.convertAndSend(CacheConstants.ACCESS_JVM_RELOAD_TOPIC,"缓存更新");
        log.info("结束预热MySQL数据至Redis缓存数据");
    }
}
