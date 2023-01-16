package cn.cleanarch.dp.common.gray.loadbalancer;

import cn.cleanarch.dp.common.core.constant.GatewayConstants;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 灰度负载均衡器
 * @author 李海峰
 * @date 2022年06月23日 17:06
 */
@Slf4j
public class GrayRoundRobinLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    final AtomicInteger position;

    final String serviceId;

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    public GrayRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceId=serviceId;
        this.serviceInstanceListSupplierProvider=serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(new Random().nextInt(1000));
    }


    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        //从当前请求头中获得请求的版本号
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map((serviceInstances) -> this.getInstanceResponse(supplier,request, serviceInstances));
    }

    public Response<ServiceInstance> getInstanceResponse(ServiceInstanceListSupplier supplier, Request request, List<ServiceInstance> serviceInstances){
        // 注册中心没有可用的实例
        if (CollUtil.isEmpty(serviceInstances)){
            log.warn("No servers available for service{}",serviceId);
            return new EmptyResponse();
        }
        if (request==null||request.getContext()==null){
            return processInstanceResponse(supplier,serviceInstances);
        }
        DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
        if (!(requestContext.getClientRequest() instanceof RequestData requestData)){
            return processInstanceResponse(supplier,serviceInstances);
        }
        String gray = requestData.getHeaders().getFirst(GatewayConstants.X_BUSINESS_METADATA_TRANSITIVE_GRAY);
        String grayTag = requestData.getHeaders().getFirst(GatewayConstants.X_BUSINESS_METADATA_TRANSITIVE_GRAY_TAG);
        if (StrUtil.isBlank(gray) || "true".equals(gray) || StrUtil.isBlank(grayTag)){
            return processInstanceResponse(supplier,serviceInstances);
        }
        // 判断nacos中有没有相对应的版本号
        List<ServiceInstance> serviceInstanceList = serviceInstances.stream().filter(serviceInstance -> {
            // 获得当前配置中的元数据信息
            Map<String, String> metadata = serviceInstance.getMetadata();
            String targetVersion = MapUtil.getStr(metadata, GatewayConstants.X_BUSINESS_METADATA_TRANSITIVE_GRAY);
            return gray.equalsIgnoreCase(targetVersion);
        }).collect(Collectors.toList());
        log.info(serviceInstances.toString());
        if (CollUtil.isNotEmpty(serviceInstanceList)){
            return processInstanceResponse(supplier,serviceInstanceList,false);
        }else {
            return processInstanceResponse(supplier,serviceInstances);
        }
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances) {
        return processInstanceResponse(supplier,serviceInstances,true);
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances,
                                                              Boolean filterGrayServiceInstance) {
        if (filterGrayServiceInstance) {
            serviceInstances = serviceInstances.stream().filter(serviceInstance -> {
                // 获得当前配置中的元数据信息
                Map<String, String> metadata = serviceInstance.getMetadata();
                String targetGrayTag = MapUtil.getStr(metadata, GatewayConstants.X_BUSINESS_METADATA_TRANSITIVE_GRAY_TAG);
                return StrUtil.isBlank(targetGrayTag);
            }).collect(Collectors.toList());
        }
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }

        // Ignore the sign bit, this allows pos to loop sequentially from 0 to
        // Integer.MAX_VALUE
        int pos = this.position.incrementAndGet() & Integer.MAX_VALUE;

        ServiceInstance instance = instances.get(pos % instances.size());

        return new DefaultResponse(instance);
    }
}