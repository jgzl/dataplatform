package cn.cleanarch.dp.common.gray;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.gray.feign.GrayFeignInterceptor;
import cn.cleanarch.dp.common.gray.loadbalancer.GrayLoadBalancerClientConfiguration;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 灰度自动化配置
 * @author 李海峰
 * @date 2022年06月23日 17:06
 */
@Configuration
@LoadBalancerClients(defaultConfiguration = GrayLoadBalancerClientConfiguration.class)
@ConditionalOnProperty(name = CommonConstants.CONFIGURATION_PREFIX+".gray.enabled",matchIfMissing = true,havingValue = "true")
public class GrayAutoConfiguration {

    @Bean
    public RequestInterceptor grayFeigeInterceptor() {
        return new GrayFeignInterceptor();
    }

}