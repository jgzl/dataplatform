package cn.cleanarch.gw.gateway.admin.gateway.service.impl;

import cn.cleanarch.gw.common.core.constant.CacheConstants;
import cn.cleanarch.gw.common.core.utils.JacksonUtil;
import cn.cleanarch.gw.common.gateway.support.DynamicRouteInitEvent;
import cn.cleanarch.gw.common.gateway.vo.GatewayFilterDefinition;
import cn.cleanarch.gw.common.gateway.vo.GatewayPredicateDefinition;
import cn.cleanarch.gw.common.gateway.vo.GatewayRouteDefinition;
import cn.cleanarch.gw.gateway.admin.gateway.domain.GatewayRouteConfDO;
import cn.cleanarch.gw.gateway.admin.gateway.mapper.GatewayRouteConfMapper;
import cn.cleanarch.gw.gateway.admin.gateway.service.GatewayRouteConfService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 网关路由
 *
 * @author li7hai26@gmail.com
 * @date 2018年11月06日10:27:55
 */
@Slf4j
@AllArgsConstructor
@Service("gatewayRouteConfService")
public class GatewayRouteConfServiceImpl extends ServiceImpl<GatewayRouteConfMapper, GatewayRouteConfDO>
        implements GatewayRouteConfService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 新增或者更新路由信息
     *
     * @param route 路由信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(GatewayRouteConfDO route) {
        // 清空Redis 缓存
        Long result = redisTemplate.opsForHash().delete(CacheConstants.ROUTE_KEY, route.getRouteId());
        log.info("清空网关路由条数:{} ", result);
        try {
            super.saveOrUpdate(route);

            GatewayRouteDefinition routeVo = new GatewayRouteDefinition();
            routeVo.setId(route.getRouteId());
            routeVo.setOrder(route.getSort());
            if (StrUtil.isNotBlank(route.getPredicates())) {
                routeVo.setPredicates(JacksonUtil.readValue(route.getPredicates(), new TypeReference<List<GatewayPredicateDefinition>>() {
                }));
            }
            if (StrUtil.isNotBlank(route.getFilters())) {
                routeVo.setFilters(JacksonUtil.readValue(route.getFilters(), new TypeReference<List<GatewayFilterDefinition>>() {
                }));
            }
            if (StrUtil.isNotBlank(route.getUri())) {
                routeVo.setUri(URI.create(route.getUri()));
            }
            if (StrUtil.isNotBlank(route.getMetadata())) {
                routeVo.setMetadata(JacksonUtil.readValue(route.getMetadata(), new TypeReference<Map<String, Object>>() {
                }));
            }
            // 插入生效路由
            // redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinitionVo.class));
            redisTemplate.opsForHash().put(CacheConstants.ROUTE_KEY, routeVo.getId(), routeVo);
            log.info("更新网关路由结束");
            // 通知网关重置路由
            redisTemplate.convertAndSend(CacheConstants.ROUTE_JVM_RELOAD_TOPIC, "路由信息,网关缓存更新");
        } catch (Exception e) {
            log.error("路由配置解析失败", e);
            // 回滚路由，重新加载即可
            SpringUtil.publishEvent(new DynamicRouteInitEvent(this));
            // 抛出异常
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 删除路由信息
     *
     * @param routeId 路由id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoute(String routeId) {
        // 清空Redis 缓存
        String[] strRouteIdArray = routeId.split(",");
        Long result = redisTemplate.opsForHash().delete(CacheConstants.ROUTE_KEY, strRouteIdArray);
        log.info("清空网关路由条数:{} ", result);
        try {
            this.remove(Wrappers.<GatewayRouteConfDO>update().lambda().in(GatewayRouteConfDO::getRouteId, strRouteIdArray));
            log.info("更新网关路由结束");
            // 通知网关重置路由
            redisTemplate.convertAndSend(CacheConstants.ROUTE_JVM_RELOAD_TOPIC, "路由信息,网关缓存更新");
        } catch (Exception e) {
            log.error("路由配置解析失败", e);
            // 回滚路由，重新加载即可
            SpringUtil.publishEvent(new DynamicRouteInitEvent(this));
            // 抛出异常
            throw new RuntimeException(e);
        }
    }
}
