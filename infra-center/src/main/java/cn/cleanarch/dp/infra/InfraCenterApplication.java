package cn.cleanarch.dp.infra;

import cn.cleanarch.dp.common.feign.EnableFeignClients;
import cn.cleanarch.dp.common.oauth.annotation.EnableResourceServer;
import com.dtflys.forest.springboot.annotation.ForestScan;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author li7hai26@gmail.com
 * @date 2021/10/8
 */
@ForestScan(basePackages = "cn.cleanarch.dp.infra.third")
@EnableAdminServer
@EnableResourceServer
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class InfraCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfraCenterApplication.class, args);
    }

}