package cn.cleanarch.dp.common.gateway.ext.base;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @Description Groovy规则引擎动态脚本实现父类
 * @Author JL
 * @Date 2022/2/21
 * @Version V1.0
 */
@Slf4j
@Setter
public abstract class BaseGroovyService {

    public RedisTemplate redisTemplate;
    public String clientIp;
    public Long groovyScriptId;
    public String ruleName;
    public String routeId;
    public String extednInfo;
    public String body;
    public Map<String, String> paramMap;

    public abstract void apply(ServerWebExchange exchange) throws Exception;
}
