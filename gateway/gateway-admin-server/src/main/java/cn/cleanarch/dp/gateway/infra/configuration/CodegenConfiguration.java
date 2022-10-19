package cn.cleanarch.dp.gateway.infra.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CodegenProperties.class)
public class CodegenConfiguration {
}
