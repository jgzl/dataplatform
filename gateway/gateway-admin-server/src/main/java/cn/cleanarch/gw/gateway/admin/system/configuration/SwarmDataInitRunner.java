package cn.cleanarch.gw.gateway.admin.system.configuration;

import cn.cleanarch.gw.common.core.constant.CacheConstants;
import cn.cleanarch.gw.gateway.admin.gateway.convert.GatewayAccessConfConvert;
import cn.cleanarch.gw.gateway.admin.gateway.service.GatewayAccessConfService;
import cn.cleanarch.gw.gateway.admin.gateway.vo.GatewayAccessConfVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author lihaifeng
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class SwarmDataInitRunner implements CommandLineRunner{

    private final GatewayAccessConfService accessConfService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始预热MySQL数据至Redis缓存数据");
        redisTemplate.delete(CacheConstants.ACCESS_CONF_KEY);
        accessConfService.list().forEach(item -> {
            GatewayAccessConfVO vo = GatewayAccessConfConvert.INSTANCE.convertDo2Vo(item);
            redisTemplate.opsForHash().put(CacheConstants.ACCESS_CONF_KEY,vo.getApiKey(),vo);
        });
        redisTemplate.convertAndSend(CacheConstants.ACCESS_CONF_JVM_RELOAD_TOPIC,"缓存更新");
        log.info("结束预热MySQL数据至Redis缓存数据");
    }
}
