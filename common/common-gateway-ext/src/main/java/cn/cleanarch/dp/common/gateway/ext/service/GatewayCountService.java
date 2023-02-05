package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayCountReq;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayCountRsp;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayCountTotalRsp;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayRouteDOCountRsp;
import cn.cleanarch.dp.common.gateway.ext.util.ApiResult;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.PageResult;
import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Description 统计接口请求业务类
 * @Author JL
 * @Date 2020/12/30
 * @Version V1.0
 */
@Service
public class GatewayCountService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private GatewayRouteService gatewayRouteService;

    /**
     * 查询路由集合统计结果
     * @param gatewayRouteDO
     * @param currentPage
     * @param pageSize
     * @return
     */
    public ApiResult countRouteList(GatewayRouteDO gatewayRouteDO, int currentPage, int pageSize){
        PageResult<GatewayRouteDO> pageResult = gatewayRouteService.pageList(gatewayRouteDO,currentPage, pageSize);
        if (pageResult.getPageSize() > 0){
            //只取当天的
            String key = GatewayRouteConstants.COUNT_DAY_KEY + DateFormatUtils.format(new Date(), Constants.DATE_FORMAT_DAY);
            Map<String,String> countMap = redisTemplate.opsForHash().entries(key);
            List<GatewayRouteDO> gatewayRouteDOList = pageResult.getLists();
            List<GatewayRouteDOCountRsp> routeCountRspList = gatewayRouteDOList.stream().map(r -> {
                GatewayRouteDOCountRsp rsp = new GatewayRouteDOCountRsp();
                BeanUtils.copyProperties(r, rsp);
                //将routeId缓存的统计值取出
                String count = countMap.get(rsp.getId());
                rsp.setCount(StringUtils.isNotBlank(count) ? Integer.parseInt(count) : 0);
                return rsp;
            }).collect(Collectors.toList());
            PageResult<GatewayRouteDOCountRsp> pageResult1 = new PageResult<>();
            pageResult1.setCurrentPage(currentPage);
            pageResult1.setPageSize(pageSize);
            pageResult1.setTotalNum(pageResult.getTotalNum());
            pageResult1.setLists(routeCountRspList);
            return new ApiResult(pageResult1);
        }
        return new ApiResult(pageResult);
    }

    /**
     * 统计按7天和按24小时、60分钟维度计算的请求总量
     * @return
     */
    public GatewayCountTotalRsp countRequestTotal(String routeId){
        GatewayCountTotalRsp rsp = new GatewayCountTotalRsp();
        rsp.setDayCounts(this.get7DayTotalApiCount(routeId));
        rsp.setHourCounts(this.get24HourTotalApiCount(routeId));
        rsp.setMinCounts(this.get60MinTotalApiCount(routeId));
        return rsp;
    }

    /**
     * 统计按7天和按24小时维度计算的请求总量
     * @return
     */
    public GatewayCountTotalRsp countRequestTotal(){
        GatewayCountTotalRsp rsp = new GatewayCountTotalRsp();
        rsp.setDayCounts(this.get7DayTotalApiCount());
        rsp.setHourCounts(this.get24HourTotalApiCount());
        return rsp;
    }

    /**
     * 统计7天内请求量数据
     */
    public GatewayCountRsp get7DayTotalApiCount(String routeId){
        return this.getCount(routeId, Constants.DAY);
    }

    /**
     * 统计24小时内请求量数据
     */
    public GatewayCountRsp get24HourTotalApiCount(String routeId){
        return this.getCount(routeId, Constants.HOUR);
    }

    /**
     * 统计60分钟内请求量数据
     */
    public GatewayCountRsp get60MinTotalApiCount(String routeId){
        return this.getCount(routeId, Constants.MIN);
    }

    /**
     * 统计7天内请求量数据
     */
    public GatewayCountRsp get7DayTotalApiCount(){
        return this.getCount(Constants.DAY);
    }

    /**
     * 统计24小时内请求量数据
     */
    public GatewayCountRsp get24HourTotalApiCount(){
        return this.getCount(Constants.HOUR);
    }

    /**
     * 统计天或小时维度的请求量数据
     * @param routeId
     * @param dateType
     * @return
     */
    private GatewayCountRsp getCount(String routeId, String dateType){
        Object [] infos = getCountInfo(dateType);
        List<String> dateList = (List<String>)infos[0];
        String countTag = (String)infos[1];
        List<Integer> counts = new ArrayList<>(dateList.size());
        List<String> dates = new ArrayList<>(dateList.size());
        for (String date : dateList){
            Map<String,String> countMap = redisTemplate.opsForHash().entries(countTag + date);
            AtomicReference<Integer> count = new AtomicReference<>(0);
            if (!CollectionUtils.isEmpty(countMap)){
                for (Map.Entry<String,String> entry : countMap.entrySet()){
                    //如果有指定routeId,则只取该routeId的数据
                    if (routeId != null) {
                        if (!entry.getKey().equals(routeId)){
                            continue;
                        }
                    }
                    // 如果为负载均衡的统计结果，则跳过，单独在负载均衡界面展示统计指标
                    if (entry.getKey().startsWith(GatewayRouteConstants.BALANCED)){
                        continue;
                    }
                    count.updateAndGet(v1 -> v1 + Integer.parseInt(entry.getValue()));
                }
            }
            dates.add(getFormatDateStr(date, dateType));
            counts.add(count.get());
        }
        GatewayCountRsp gatewayCountRsp = new GatewayCountRsp();
        gatewayCountRsp.setDates(dates);
        gatewayCountRsp.setCounts(counts);
        return gatewayCountRsp;
    }


    /**
     * 统计天或小时维度的请求量数据
     * @param dateType
     * @return
     */
    private GatewayCountRsp getCount(String dateType){
        return this.getCount(null, dateType);
    }

    /**
     * 统计路由请求数据
     * @param gatewayCountReq      请求对象
     * @param isBalanced    是否负载
     * @return
     */
    public ApiResult count(GatewayCountReq gatewayCountReq, boolean isBalanced){
        Assert.notNull(gatewayCountReq, "未获取到对象");
        List<String> routeIds = gatewayCountReq.getRouteIds();
        Assert.isTrue(routeIds != null, "未获取到路由ID");
        String dateType = StringUtils.isNotBlank(gatewayCountReq.getDateType()) ? gatewayCountReq.getDateType() : Constants.MIN;
        Object [] infos = getCountInfo(dateType);
        List<String> dateList = (List<String>)infos[0];
        List<String> dates = new ArrayList<>(dateList.size());
        String countTag = (String)infos[1];
        String balancedId = gatewayCountReq.getBalancedId().replaceAll("-","");
        Map<String,List<Integer>> countMap = new LinkedHashMap<>(routeIds.size());
        for (String date : dateList){
            for (String routeId : routeIds){
                String key = countTag + date;
                String field = (isBalanced ? GatewayRouteConstants.BALANCED + "-" + balancedId  + "-" : "") + routeId;
                String count = (String) redisTemplate.opsForHash().get(key, field);
                List<Integer> counts = countMap.get(routeId);
                if (counts == null){
                    counts = new ArrayList<>();
                }
                counts.add(count != null ? Integer.parseInt(count) : 0);
                countMap.put(routeId, counts);
            }
            dates.add(getFormatDateStr(date, dateType));
        }

        List<GatewayCountRsp.CountData> datas = new ArrayList<>();
        GatewayCountRsp rsp = new GatewayCountRsp();
        rsp.setDatas(datas);
        rsp.setDates(dates);
        countMap.forEach((k,v)->{
            GatewayCountRsp.CountData data = new GatewayCountRsp.CountData();
            data.setRouteId(k);
            data.setCounts(v.toArray(new Integer [v.size()]));
            datas.add(data);
        });
        return new ApiResult(rsp);
    }

    /**
     * 截取时间格式，方便前端展示
     * @param date
     * @param dateType
     * @return
     */
    private String getFormatDateStr(String date, String dateType){
        if (Constants.HOUR.equals(dateType)){
            //yyyyMMddHH=>HH
            return date.substring(8);
        } else if (Constants.MIN.equals(dateType)){
            //yyyyMMddHHmm=>mm
            return date.substring(10);
        }
        return date;
    }

    /**
     * 获取统计维度信息
     * @param dateType
     * @return
     */
    private Object[] getCountInfo(String dateType){
        List<String> dates;
        String countTag ;
        if (Constants.MIN.equals(dateType)){
            dates = this.getCountMins();
            countTag =  GatewayRouteConstants.COUNT_MIN_KEY;
        }else if (Constants.HOUR.equals(dateType)){
            dates = this.getCountHours();
            countTag =  GatewayRouteConstants.COUNT_HOUR_KEY;
        }else if (Constants.DAY.equals(dateType)){
            dates = this.getCountDays();
            countTag =  GatewayRouteConstants.COUNT_DAY_KEY;
        }else {
            throw new IllegalArgumentException("统计时间类型错误");
        }
        return new Object[]{dates, countTag};
    }

    /**
     * 按天生成统计范围（计算一周内的统计值）
     * @return
     */
    private List<String> getCountDays(){
        List<String>  days = new ArrayList<>();
        for (int i = 6; i>=0; i--){
            Date dayDate = DateUtils.addDays(new Date(), - i);
            String day = DateFormatUtils.format(dayDate, Constants.YYYYMMDD);
            days.add(day);
        }
        return days;
    }

    /**
     * 按小时生成统计范围（计算一天内的统计值）
     * @return
     */
    private List<String> getCountHours(){
        List<String> hours = new ArrayList<>();
        for (int i = 23; i>=0; i--){
            hours.add(DateFormatUtils.format(DateUtils.addHours(new Date(), - i), Constants.YYYYMMDDHH));
        }
        return hours;
    }

    /**
     * 按分钟生成统计范围
     * @return
     */
    private List<String> getCountMins(){
        List<String> mins = new ArrayList<>();
        for (int i = 59; i>=0; i--){
            mins.add(DateFormatUtils.format(DateUtils.addMinutes(new Date(), - i), Constants.YYYYMMDDHHMM));
        }
        return mins;
    }



}
