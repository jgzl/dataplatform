package cn.cleanarch.dp.gateway;

import cn.cleanarch.dp.common.feign.EnableFeignClients;
import cn.cleanarch.dp.common.gateway.annotation.EnableDynamicRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author li7hai26@gmail.com
 * @date 2021/10/8
 */
@EnableFeignClients
@EnableDynamicRoute
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}