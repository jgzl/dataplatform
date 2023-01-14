package cn.cleanarch.dp.common.gateway.support;

import cn.cleanarch.dp.gateway.admin.vo.GatewayAccessVO;
import lombok.experimental.UtilityClass;

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
public class GatewayAccessCacheHolder {

    private final Map<String, GatewayAccessVO> cache = new ConcurrentHashMap<>();

    /**
     * 获取缓存的全部对象
     *
     * @return routeList
     */
    public List<GatewayAccessVO> getList() {
        List<GatewayAccessVO> list = new ArrayList<>();
        cache.forEach((route,vo) -> list.add(vo));
        return list;
    }

    /**
     * 获取缓存的对象
     *
     */
    public GatewayAccessVO get(String key) {
        return cache.get(key);
    }

    /**
     * 更新缓存
     *
     * @param list 缓存列表
     */
    public void setList(List<GatewayAccessVO> list) {
        list.forEach(item -> cache.put(item.getApiKey(), item));
    }

    /**
     * 清空缓存
     */
    public void removeList() {
        cache.clear();
    }

}
