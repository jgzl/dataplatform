package cn.cleanarch.dp.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author li7hai26@gmail.com
 * @date 2021/10/8
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class MonitorCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorCenterApplication.class, args);
    }

}