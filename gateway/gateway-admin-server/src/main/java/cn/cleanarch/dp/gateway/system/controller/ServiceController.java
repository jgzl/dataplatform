package cn.cleanarch.dp.gateway.system.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 服务查看管理模块
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/20
 */
@Slf4j
@AllArgsConstructor
@RestController
public class ServiceController {

    private final Environment environment;

    private final DiscoveryClient discoveryClient;

    /**
     * 获取当前服务所有节点(虚拟机VM,k8s pod)
     *
     * @return
     */
    @GetMapping("/services/current")
    public R<ServiceInfo> currentServices() {
        String serviceNameId = environment.getProperty("spring.application.name");
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceNameId);
        ServiceInfo serviceInfo = new ServiceInfo(serviceNameId, instances);
        return R.success(serviceInfo);
    }

    /**
     * 获取所有服务所有节点(虚拟机VM,k8s pod)
     *
     * @return
     */
    @GetMapping("/services/all")
    public R<List<ServiceInfo>> allServices() {
        List<String> services = discoveryClient.getServices();
        List<ServiceInfo> serviceInfos = Lists.newArrayList();
        if (CollUtil.isNotEmpty(services)) {
            for (String serviceNameId : services) {
                List<ServiceInstance> instances = discoveryClient.getInstances(serviceNameId);
                ServiceInfo serviceInfo = new ServiceInfo(serviceNameId, instances);
                serviceInfos.add(serviceInfo);
            }
        }
        return R.success(serviceInfos);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ServiceInfo {
        private String serviceNameId;
        private List<ServiceInstance> serviceInstances;
    }
}
