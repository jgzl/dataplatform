package cn.cleanarch.dp.tool;

import cn.cleanarch.dp.common.feign.EnableFeignClients;
import cn.cleanarch.dp.common.oauth.annotation.EnableResourceServer;
import com.dtflys.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author li7hai26@gmail.com
 * @date 2021/10/8
 */
@ForestScan(basePackages = "cn.cleanarch.dp.tool.third")
@EnableResourceServer
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolApplication.class, args);
    }

}