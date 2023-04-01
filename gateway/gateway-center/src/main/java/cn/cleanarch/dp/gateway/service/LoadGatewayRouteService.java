package cn.cleanarch.dp.gateway.service;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRouteDO;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
import cn.cleanarch.dp.gateway.filter.IpGatewayFilter;
import cn.cleanarch.dp.gateway.filter.TokenGatewayFilter;
import cn.cleanarch.dp.gateway.filter.ClientIdGatewayFilter;
import cn.cleanarch.dp.gateway.vo.GatewayFilterDefinition;
import cn.cleanarch.dp.gateway.vo.GatewayPredicateDefinition;
import cn.cleanarch.dp.gateway.vo.GatewayRouteConfig;
import cn.cleanarch.dp.gateway.vo.GatewayRouteDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.*;

/**
 * @Description 将数据转换为Gateway网关需要数据格式，并返回服务路由对象
 * @Author jianglong
 * @Date 2020/05/25
 * @Version V1.0
 */
@Service
public class LoadGatewayRouteService {

    @Qualifier("uriKeyResolver")
    @Resource
    private KeyResolver uriKeyResolver;

    @Qualifier("hostAddrKeyResolver")
    @Resource
    private KeyResolver hostAddrKeyResolver;

    @Qualifier("requestIdKeyResolver")
    @Resource
    private KeyResolver requestIdKeyResolver;

    /**
     * 把传递进来的参数转换成路由对象
     * @param gwdefinition
     * @return
     */
    public RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(gwdefinition.getId());
        definition.setOrder(gwdefinition.getOrder());
        //设置断言
        List<PredicateDefinition> pdList=new ArrayList<>();
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList=gwdefinition.getPredicates();
        for (GatewayPredicateDefinition gpDefinition: gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);
        //设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilterDefinition> gatewayFilters = gwdefinition.getFilters();
        for(GatewayFilterDefinition filterDefinition : gatewayFilters){
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        definition.setFilters(filters);
        definition.setUri(this.getURI(gwdefinition.getUri()));
        return definition;
    }

    /**
     * 封装网关路由参数，返回RouteDefinition
     * @param r
     * @return
     */
    public RouteDefinition loadRouteDefinition(GatewayRouteDO r){
        RouteDefinition definition = new RouteDefinition();
        definition.setId(r.getId());
        definition.setOrder(0);
        List<PredicateDefinition> predicates = new ArrayList<>();
        definition.setPredicates(predicates);
        List<FilterDefinition> filters = new ArrayList<>();
        definition.setFilters(filters);
        definition.setUri(this.getURI(r.getUri()));
        //权重（注意此处，当权重值小于等于0，则表示无流量流入，不创建网关）
        if (r.getWeight() != null && r.getWeight() > 0) {
            predicates.add(setPredicateDefinition(GatewayRouteConstants.WEIGHT, r.getWeightName(), String.valueOf(r.getWeight())));
        }
        //断言路径
        if (StringUtils.isNotBlank(r.getPath())) {
            predicates.add(setPredicateDefinition(GatewayRouteConstants.PATH, r.getPath()));
        }
        //请求模式
        if (StringUtils.isNotBlank(r.getMethod())) {
            predicates.add(setPredicateDefinition(GatewayRouteConstants.METHOD, r.getMethod()));
        }
        //断言主机
        if (StringUtils.isNotBlank(r.getHost())) {
            String[] parameters = r.getHost().split(Constants.SEPARATOR_SIGN);
            predicates.add(setPredicateDefinition(GatewayRouteConstants.HOST, parameters));
        }
        //断言远程地址
        if (StringUtils.isNotBlank(r.getRemoteAddr())) {
            String[] parameters = r.getRemoteAddr().split(Constants.SEPARATOR_SIGN);
            predicates.add(setPredicateDefinition(GatewayRouteConstants.REMOTE_ADDR, parameters));
        }
        //断言Header
        if (StringUtils.isNotBlank(r.getHeader())) {
            String[] parameters = r.getHeader().split(Constants.SEPARATOR_SIGN);
            predicates.add(setPredicateDefinition(GatewayRouteConstants.HEADER, parameters));
        }
        //URL截取方式
        if (r.getStripPrefix() != null && r.getStripPrefix() > 0) {
            filters.add(setFilterDefinition(GatewayRouteConstants.STRIP_PREFIX, String.valueOf(r.getStripPrefix())));
        }
        //请求参数
        if (StringUtils.isNotBlank(r.getRequestParameter())) {
            String[] parameters = r.getRequestParameter().split(Constants.SEPARATOR_SIGN);
            filters.add(setFilterDefinition(GatewayRouteConstants.ADD_REQUEST_PARAMETER, parameters));
        }
        //重写Path路径
        if (StringUtils.isNotBlank(r.getRewritePath())) {
            String[] parameters = r.getRewritePath().split(Constants.SEPARATOR_SIGN);
            filters.add(setFilterDefinition(GatewayRouteConstants.REWRITE_PATH, parameters));
        }
        //鉴权
        if (StringUtils.isNotBlank(r.getFilterAuthorizeName())){
            filters.add(setFilterDefinition(GatewayRouteConstants.AUTHORIZE, GatewayRouteConstants.TRUE));
        }
        //过滤器,id,ip,token
        if (StringUtils.isNotBlank(r.getFilterGatewaName())) {
            String names = r.getFilterGatewaName();
            if (names.contains(GatewayRouteConstants.IP)) {
                filters.add(setFilterDefinition(GatewayRouteConstants.Secure.SECURE_IP, GatewayRouteConstants.TRUE));
            }
            if (names.contains(GatewayRouteConstants.ID)) {
                filters.add(setFilterDefinition(GatewayRouteConstants.Secure.SECURE_CLIENT_ID, GatewayRouteConstants.TRUE));
            }
            if (names.contains(GatewayRouteConstants.TOKEN)) {
                filters.add(setFilterDefinition(GatewayRouteConstants.Secure.SECURE_TOKEN, GatewayRouteConstants.TRUE));
            }
        }
        //熔断
        if (StringUtils.isNotBlank(r.getFilterHystrixName())) {
            Map<String, String> args = new LinkedHashMap<>();
            FilterDefinition filter = new FilterDefinition();
            if (r.getFilterHystrixName().equals(GatewayRouteConstants.Hystrix.DEFAULT)) {
                filter.setName(GatewayRouteConstants.Hystrix.DEFAULT_NAME);
                args.put(GatewayRouteConstants.Hystrix.NAME, GatewayRouteConstants.Hystrix.DEFAULT_HYSTRIX_NAME);
                args.put(GatewayRouteConstants.Hystrix.FALLBACK_URI, GatewayRouteConstants.Hystrix.DEFAULT_FALLBACK_URI + r.getId());
            } else {
                filter.setName(GatewayRouteConstants.Hystrix.CUSTOM_NAME);
                //自定义名称必需保持业务的唯一性，否则所有自定义熔断将共用一个配置（用id是因为值短，只要能保证hystrixName不重复就行）
                args.put(GatewayRouteConstants.Hystrix.NAME, GatewayRouteConstants.Hystrix.CUSTOM_HYSTRIX_NAME + r.getId());
                args.put(GatewayRouteConstants.Hystrix.FALLBACK_URI, GatewayRouteConstants.Hystrix.CUSTOM_FALLBACK_URI + r.getId());
            }
            filter.setArgs(args);
            filters.add(filter);
        }
        //限流
        if (StringUtils.isNotBlank(r.getFilterRateLimiterName())) {
            String name = r.getFilterRateLimiterName();
            Map<String, String> args = new LinkedHashMap<>();
            FilterDefinition filter = new FilterDefinition();
            filter.setName(GatewayRouteConstants.Limiter.CUSTOM_REQUEST_RATE_LIMITER);
            if (name.equals(GatewayRouteConstants.IP)) {
                args.put(GatewayRouteConstants.Limiter.KEY_RESOLVER,  GatewayRouteConstants.Limiter.HOST_ADDR_KEY_RESOLVER);
            } else if (name.equals(GatewayRouteConstants.URI)) {
                args.put(GatewayRouteConstants.Limiter.KEY_RESOLVER, GatewayRouteConstants.Limiter.URI_KEY_RESOLVER);
            } else if (name.equals(GatewayRouteConstants.REQUEST_ID)) {
                args.put(GatewayRouteConstants.Limiter.KEY_RESOLVER, GatewayRouteConstants.Limiter.REQUEST_ID_KEY_RESOLVER);
            }
            args.put(GatewayRouteConstants.Limiter.REPLENISH_RATE , r.getReplenishRate() + "");
            args.put(GatewayRouteConstants.Limiter.BURS_CAPACITY, r.getBurstCapacity() + "");
            filter.setArgs(args);
            filters.add(filter);
        }
        return definition;
    }

    /**
     * 封装Predicate属性值，返回PredicateDefinition对象
     * @param name
     * @param values
     * @return
     */
    private PredicateDefinition setPredicateDefinition(String name, String ... values){
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> args = new HashMap<String, String>();
        predicate.setName(name);
        int i=0;
        for (String value : values){
            args.put(GatewayRouteConstants._GENKEY_ + i, value);
            i++;
        }
        predicate.setArgs(args);
        return predicate;
    }

    /**
     * 封装Filter属性值，返回FilterDefinition对象
     * @param name
     * @param values
     * @return
     */
    private FilterDefinition setFilterDefinition(String name, String ... values){
        FilterDefinition filter = new FilterDefinition();
        filter.setName(name);
        Map<String, String> args = new LinkedHashMap<>();
        int i=0;
        for (String value : values){
            args.put(GatewayRouteConstants._GENKEY_ + i, value);
            i++;
        }
        filter.setArgs(args);
        return filter;
    }

    /**
     * 封状URI
     * @param uriStr
     * @return
     */
    private URI getURI(String uriStr){
        URI uri ;
        if(uriStr.startsWith(Constants.HTTP)){
            uri = UriComponentsBuilder.fromHttpUrl(uriStr).build().toUri();
        }else{
            // uri为lb://consumer-service
            uri = URI.create(uriStr);
        }
        return uri;
    }

    /**
     * 封装网关路由对象，用于系统执行config初始化加载时使用
     * @param r     自定义服务路由对象
     * @return
     */
    public GatewayRouteConfig loadRouteConfig(GatewayRouteDO r){
        GatewayRouteConfig config = new GatewayRouteConfig();
        config.setId(r.getId());
        config.setOrder(0);
        config.setUri(r.getUri());
        //断言路径
        if (StringUtils.isNotBlank(r.getPath())) {
            config.setPath(r.getPath());
        }
        //请求模式
        if (StringUtils.isNotBlank(r.getMethod())) {
            config.setMethod(r.getMethod());
        }
        //断言主机
        if (StringUtils.isNotBlank(r.getHost())) {
            config.setHost(r.getHost());
        }
        //断言远程地址
        if (StringUtils.isNotBlank(r.getRemoteAddr())) {
            config.setRemoteAddr(r.getRemoteAddr());
        }
        //断言截取
        if (r.getStripPrefix() != null && r.getStripPrefix() > 0) {
            config.setStripPrefix(r.getStripPrefix().intValue());
        }
        //请求参数
        if (StringUtils.isNotBlank(r.getRequestParameter())) {
            String[] parameters = r.getRequestParameter().split(Constants.SEPARATOR_SIGN);
            config.setRequestParameterName(parameters[0]);
            config.setRequestParameterValue(parameters[1]);
        }
        //重写Path路径
        if (StringUtils.isNotBlank(r.getRewritePath())) {
            String[] parameters = r.getRewritePath().split(Constants.SEPARATOR_SIGN);
            config.setRewritePathName(parameters[0]);
            config.setRewritePathValue(parameters[1]);
        }
        //过滤器,id,ip,token
        if (StringUtils.isNotBlank(r.getFilterGatewaName())) {
            List<GatewayFilter> filters = new ArrayList<>();
            String names = r.getFilterGatewaName();
            if (names.contains(GatewayRouteConstants.IP)) {
                filters.add(new IpGatewayFilter(r.getId()));
            }
            if (names.contains(GatewayRouteConstants.ID)) {
                filters.add(new ClientIdGatewayFilter(r.getId()));
            }
            if (names.contains(GatewayRouteConstants.TOKEN)) {
                filters.add(new TokenGatewayFilter(r.getId()));
            }
            config.setGatewayFilter(filters.toArray(new GatewayFilter[]{}));
        }
        //限流
        if (StringUtils.isNotBlank(r.getFilterRateLimiterName())) {
            String name = r.getFilterRateLimiterName();
            KeyResolver keyResolver = null;
            if (name.equals(GatewayRouteConstants.IP)) {
                keyResolver = hostAddrKeyResolver;
            } else if (name.equals(GatewayRouteConstants.URI)) {
                keyResolver = uriKeyResolver;
            } else if (name.equals(GatewayRouteConstants.REQUEST_ID)) {
                keyResolver = requestIdKeyResolver;
            }
            config.setKeyResolver(keyResolver);
            config.setReplenishRate(r.getReplenishRate());
            config.setBurstCapacity(r.getBurstCapacity());
        }
        //鉴权
        if (StringUtils.isNotBlank(r.getFilterAuthorizeName())){
            config.setAuthorize(true);
        }
        return config;
    }


}
