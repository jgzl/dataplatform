package cn.cleanarch.dp.gateway.admin.fish.timer;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayMonitorDO;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayMonitorService;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
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
    private GatewayMonitorService gatewayMonitorService;

    private final static int MAX_TIME_OUT = 30 * 1000;

    /**
     * 每30秒钟执行一次缓存同步
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void syncRequestTimeCache(){
        log.info("执行定时任务：网关路由监控告警记录同步到redis缓存...");
        Map<String, String> dataMap = redisTemplate.opsForHash().entries(GatewayRouteConstants.SYNC_REQUEST_TIME_KEY);
        if (dataMap == null || dataMap.size() <= 0){
            return ;
        }
        //读取所有监控配置，判断哪些已经超过30s，没有客户端请求，将其放入到监控队列
        List<GatewayMonitorDO> gatewayMonitorDOList = gatewayMonitorService.validRouteMonitorList();
        if (CollectionUtils.isEmpty(gatewayMonitorDOList)){
            return ;
        }
        String time;
        long validTime;
        for (GatewayMonitorDO gatewayMonitorDO : gatewayMonitorDOList){
            time = dataMap.get(gatewayMonitorDO.getId());
            if (time != null){
                //如果没有超出MAX_TIME_OUT值，则不进行监控
                validTime = System.currentTimeMillis() - Long.parseLong(time);
                if (validTime <= MAX_TIME_OUT){
                    continue;
                }
            }
            //如果已是2告警状态
            if (Constants.ALARM.equals(gatewayMonitorDO.getStatus())){
                //是否开启告警重试，0开启，1禁用
                if (Constants.NO.equals(gatewayMonitorDO.getRecover())){
                    //未开启告警重试，就无需再重复向服务端发起心跳检测
                    continue;
                }
            }
            redisTemplate.opsForList().rightPush(GatewayRouteConstants.MONITOR_QUEUE_KEY, gatewayMonitorDO.getId());
        }
    }

}
