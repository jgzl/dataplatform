package cn.cleanarch.dp.gateway.admin.fish.timer;

import cn.cleanarch.dp.common.gateway.ext.dataobject.Monitor;
import cn.cleanarch.dp.common.gateway.ext.service.MonitorService;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.RouteConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description 获取所有网关路由服务被客户端访问的最后时间记录，判断30s内是否有客户端请求
 * @Author JL
 * @Date 2021/04/15
 * @Version V1.0
 */
@Slf4j
@Service
public class TimerRequestAlarmService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MonitorService monitorService;

    private final static int MAX_TIME_OUT = 30 * 1000;

    /**
     * 每30秒钟执行一次缓存同步
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void syncRequestTimeCache(){
        log.info("执行定时任务：网关路由监控告警记录同步到redis缓存...");
        Map<String, String> dataMap = redisTemplate.opsForHash().entries(RouteConstants.SYNC_REQUEST_TIME_KEY);
        if (dataMap == null || dataMap.size() <= 0){
            return ;
        }
        //读取所有监控配置，判断哪些已经超过30s，没有客户端请求，将其放入到监控队列
        List<Monitor> monitorList = monitorService.validRouteMonitorList();
        if (CollectionUtils.isEmpty(monitorList)){
            return ;
        }
        String time;
        long validTime;
        for (Monitor monitor : monitorList){
            time = dataMap.get(monitor.getId());
            if (time != null){
                //如果没有超出MAX_TIME_OUT值，则不进行监控
                validTime = System.currentTimeMillis() - Long.parseLong(time);
                if (validTime <= MAX_TIME_OUT){
                    continue;
                }
            }
            //如果已是2告警状态
            if (Constants.ALARM.equals(monitor.getStatus())){
                //是否开启告警重试，0开启，1禁用
                if (Constants.NO.equals(monitor.getRecover())){
                    //未开启告警重试，就无需再重复向服务端发起心跳检测
                    continue;
                }
            }
            redisTemplate.opsForList().rightPush(RouteConstants.MONITOR_QUEUE_KEY, monitor.getId());
        }
    }

}
