package cn.cleanarch.dp.common.gateway.support;

import lombok.experimental.UtilityClass;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author li7hai26@gmail.com
 * @date 2019-08-16
 * <p>
 * 路由缓存工具类
 */
@UtilityClass
public class RouteDefinitionCacheHolder {

    private final Map<String, RouteDefinition> cache = new ConcurrentHashMap<>();

    /**
     * 获取缓存的全部对象
     *
     * @return routeList
     */
    public List<RouteDefinition> getRouteList() {
        List<RouteDefinition> routeList = new ArrayList<>();
        cache.forEach((route,vo) -> routeList.add(vo));
        return routeList;
    }

    /**
     * 更新缓存
     *
     * @param routeList 缓存列表
     */
    public void setRouteList(List<RouteDefinition> routeList) {
        routeList.forEach(route -> cache.put(route.getId(), route));
    }

    /**
     * 清空缓存
     */
    public void removeRouteList() {
        cache.clear();
    }

}
