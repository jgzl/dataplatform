package cn.cleanarch.dp.gateway.admin.gateway.configuration;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.gateway.admin.gateway.service.GatewayRouteConfService;
import cn.cleanarch.dp.gateway.vo.GatewayFilterDefinitionVO;
import cn.cleanarch.dp.gateway.vo.GatewayPredicateDefinitionVO;
import cn.cleanarch.dp.gateway.vo.GatewayRouteDefinitionVO;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.Async;

import java.net.URI;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Slf4j
@Configuration
public class DynamicRouteInitRunner {

    private final RedisTemplate<String,Object> redisTemplate;

    private final GatewayRouteConfService routeConfService;

    @Async
    @Order
    @EventListener({WebServerInitializedEvent.class, DynamicRouteInitEvent.class})
    public void initRoute() {
        redisTemplate.delete(CacheConstants.ROUTE_KEY);
        log.info("开始初始化网关路由");

        routeConfService.list().forEach(route -> {
            GatewayRouteDefinitionVO vo = new GatewayRouteDefinitionVO();
            vo.setId(route.getRouteId());
            vo.setUri(this.getURI(route.getUri()));
            vo.setOrder(route.getSort());

            JSONArray filterObj = JSONUtil.parseArray(route.getFilters());
            vo.setFilters(filterObj.toList(GatewayFilterDefinitionVO.class));
            JSONArray predicateObj = JSONUtil.parseArray(route.getPredicates());
            vo.setPredicates(predicateObj.toList(GatewayPredicateDefinitionVO.class));

            log.info("加载路由ID：{},{}", route.getRouteId(), vo);
            redisTemplate.opsForHash().put(CacheConstants.ROUTE_KEY, route.getRouteId(), vo);
        });

        // 通知网关重置路由
        redisTemplate.convertAndSend(CacheConstants.ROUTE_JVM_RELOAD_TOPIC, "路由信息,网关缓存更新");
        log.debug("初始化网关路由结束 ");
    }

    /**
     * redis 监听配置,监听 gateway_redis_route_reload_topic,重新加载Redis
     *
     */
    public DynamicRouteInitRunner(RedisTemplate<String,Object> redisTemplate,
                                  GatewayRouteConfService routeConfService,
                                  RedisMessageListenerContainer container) {
        this.redisTemplate = redisTemplate;
        this.routeConfService = routeConfService;
        container.addMessageListener((message, bytes) -> {
            log.warn("接收到重新Redis 重新加载路由事件");
            initRoute();
        }, new ChannelTopic(CacheConstants.ROUTE_REDIS_RELOAD_TOPIC));
    }

    /**
     * 封状URI
     * @param uriStr
     * @return
     */
    private URI getURI(String uriStr){
        return URI.create(uriStr);
    }

}