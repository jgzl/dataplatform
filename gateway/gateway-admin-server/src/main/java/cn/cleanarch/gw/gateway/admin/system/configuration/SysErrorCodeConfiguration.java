package cn.cleanarch.gw.gateway.admin.system.configuration;

import cn.cleanarch.gw.gateway.admin.system.service.ErrorCodeAutoGenerator;
import cn.cleanarch.gw.gateway.admin.system.service.ErrorCodeFrameworkService;
import cn.cleanarch.gw.gateway.admin.system.service.ErrorCodeLoader;
import cn.cleanarch.gw.gateway.admin.system.service.impl.ErrorCodeAutoGeneratorImpl;
import cn.cleanarch.gw.gateway.admin.system.service.impl.ErrorCodeLoaderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 错误码配置类
 */
@Configuration
@EnableConfigurationProperties(SysErrorCodeProperties.class)
@EnableScheduling // 开启调度任务的功能，因为 ErrorCodeRemoteLoader 通过定时刷新错误码
public class SysErrorCodeConfiguration {

    @Bean
    public ErrorCodeAutoGenerator errorCodeAutoGenerator(@Value("${spring.application.name}") String applicationName,
                                                         SysErrorCodeProperties sysErrorCodeProperties,
                                                         ErrorCodeFrameworkService errorCodeFrameworkService) {
        return new ErrorCodeAutoGeneratorImpl(applicationName, sysErrorCodeProperties.getConstantsClassList(),
                errorCodeFrameworkService);
    }

    @Bean
    public ErrorCodeLoader errorCodeLoader(@Value("${spring.application.name}") String applicationName,
                                           ErrorCodeFrameworkService errorCodeFrameworkService) {
        return new ErrorCodeLoaderImpl(applicationName, errorCodeFrameworkService);
    }

}
