package cn.cleanarch.dp.gateway.fish.cache;

import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 缓存接口请求统计缓存
 * @Author jianglong
 * @Date 2020/07/07
 * @Version V1.0
 */
public class GatewayRouteCountCache {

    private static final ConcurrentHashMap<String,Integer> cacheMap = new ConcurrentHashMap<>();

    public static void put(final String key,final Integer value){
        Assert.notNull(key, "hash map key cannot is null");
        Assert.notNull(value, "hash map value cannot is null");
        cacheMap.put(key, value);
    }

    public static Integer get(final String key){
        return cacheMap.get(key);
    }

    public static synchronized void remove(final String key){
        cacheMap.remove(key);
    }

    public static synchronized void clear(){
        cacheMap.clear();
    }

    public static ConcurrentHashMap<String,Integer> getCacheMap(){
        return cacheMap;
    }
}
