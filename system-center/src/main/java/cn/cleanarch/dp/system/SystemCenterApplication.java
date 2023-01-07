package cn.cleanarch.dp.system;

import cn.cleanarch.dp.common.feign.EnableFeignClients;
import cn.cleanarch.dp.common.oauth.annotation.EnableResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author li7hai26@gmail.com
 * @date 2021/10/8
 */
@EnableResourceServer
@EnableFeignClients
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication
public class SystemCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemCenterApplication.class, args);
    }

}