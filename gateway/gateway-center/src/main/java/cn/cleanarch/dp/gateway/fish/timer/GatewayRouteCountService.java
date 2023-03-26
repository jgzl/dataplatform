package cn.cleanarch.dp.gateway.fish.timer;

import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
import cn.cleanarch.dp.gateway.fish.cache.GatewayRouteCountCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description 定时执行路由访问量统计业务类
 * @Author jianglong
 * @Date 2020/07/07
 * @Version V1.0
 */
@Slf4j
@Service
public class GatewayRouteCountService {

    @Resource
    private RedisTemplate redisTemplate;

    private static final String REFRESH = ":refresh";

    /**
     * 每1分钟执行一次缓存同步
     */
    @Scheduled(cron = "0 * * * * ?")
    public void writerCache(){
        log.info("执行定时任务：统计数据同步到redis缓存...");
        //保存按分钟统计的数据
        ConcurrentHashMap<String,Integer> cacheMap = GatewayRouteCountCache.getCacheMap();
        //深克隆map
        ConcurrentHashMap<String,Integer> minMap = new ConcurrentHashMap<>(cacheMap.size());
        minMap.putAll(cacheMap);
        //每次统计完清除缓存统计数据，重新记录下一分钟请求量
        GatewayRouteCountCache.clear();

        //保存按分钟统计的数据,数据缓存1小时
        String freshKey = GatewayRouteConstants.GATEWAY_ROUTE_COUNT_MIN_KEY + REFRESH;
        String minKey = GatewayRouteConstants.GATEWAY_ROUTE_COUNT_MIN_KEY + ":" + DateFormatUtils.format(new Date(), Constants.YYYYMMDDHHMM);
        String min =  DateFormatUtils.format(new Date(), Constants.YYYYMMDDHHMM);
        String minFresh = (String) redisTemplate.opsForValue().get(freshKey);
        if (minFresh == null || Long.parseLong(min) > Long.parseLong(minFresh)){
            this.recordCountCache(freshKey, min, minKey, minMap, 1, TimeUnit.HOURS);
        }else {
            this.syncCountCache(minKey, minMap);
        }

        //保存按小时统计的数据,数据缓存24小时
        freshKey = GatewayRouteConstants.GATEWAY_ROUTE_COUNT_HOUR_KEY + REFRESH;
        String hourKey = GatewayRouteConstants.GATEWAY_ROUTE_COUNT_HOUR_KEY + ":" + DateFormatUtils.format(new Date(), Constants.YYYYMMDDHH);
        String hour =  DateFormatUtils.format(new Date(), Constants.YYYYMMDDHH);
        String hourFresh = (String) redisTemplate.opsForValue().get(freshKey);
        if (hourFresh == null || Long.parseLong(hour) > Long.parseLong(hourFresh)){
            this.recordCountCache(freshKey, hour, hourKey, minMap, 24, TimeUnit.HOURS);
        }else {
            this.syncCountCache(hourKey, minMap);
        }

        //保存按天统计的数据,数据缓存7天
        freshKey = GatewayRouteConstants.GATEWAY_ROUTE_COUNT_DAY_KEY + REFRESH;
        String dayKey = GatewayRouteConstants.GATEWAY_ROUTE_COUNT_DAY_KEY + ":" + DateFormatUtils.format(new Date(), Constants.YYYYMMDD);
        String day =  DateFormatUtils.format(new Date(), Constants.YYYYMMDD);
        String dayFresh = (String) redisTemplate.opsForValue().get(freshKey);
        if (dayFresh == null || Long.parseLong(day) > Long.parseLong(dayFresh)){
            this.recordCountCache(freshKey, day, dayKey, minMap, 7, TimeUnit.DAYS);
        }else {
            this.syncCountCache(dayKey, minMap);
        }
    }

    /**
     * 设置缓存数据
     * @param freshKey
     * @param freshValue
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    private void recordCountCache(String freshKey, String freshValue, String key, ConcurrentHashMap<String,Integer> value, int timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(freshKey, freshValue);
        boolean exist = redisTemplate.hasKey(key);
        Map<String,String> cacheMap = new HashMap<>(value.size());
        value.forEach((k,v)->cacheMap.put(k,String.valueOf(v)));
        redisTemplate.opsForHash().putAll(key, cacheMap);
        //新增key设置过期时间
        if (!exist){
            //redis过期清除指定KEY缓存数据
            redisTemplate.expire(key, timeout, timeUnit);
        }
    }

    /**
     * 考虑网关服务的集群式架构设计，需要累加已知统计缓存数据
     * @param key
     * @param map
     */
    public void syncCountCache(String key, Map<String,Integer> map){
        map.forEach((k,v)->{
            String value = (String) redisTemplate.opsForHash().get(key, k);
            if (StringUtils.isNotBlank(value)){
                int count = 0;
                try{
                    count = Integer.parseInt(value);
                }catch(Exception e){
                    log.error("redis get key:{} & field:{} to value is null", key, k);
                }
                v += count;
            }
            redisTemplate.opsForHash().put(key, k, String.valueOf(v));
        });
    }

}
