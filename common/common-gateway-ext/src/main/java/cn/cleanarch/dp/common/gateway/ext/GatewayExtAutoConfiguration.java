package cn.cleanarch.dp.common.gateway.ext;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableJpaRepositories("cn.cleanarch.dp.common.gateway.ext.dao")
@EntityScan("cn.cleanarch.dp.common.gateway.ext.dataobject")
public class GatewayExtAutoConfiguration {
}
