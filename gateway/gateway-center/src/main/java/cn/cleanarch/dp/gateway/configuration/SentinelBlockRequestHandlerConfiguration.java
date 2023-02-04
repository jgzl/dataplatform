package cn.cleanarch.dp.gateway.configuration;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册处理器
 * @author li7hai26@gmail.com
 */
@Slf4j
@Configuration
public class SentinelBlockRequestHandlerConfiguration implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("开始注册Sentinel处理器");
        GatewayCallbackManager.setBlockHandler(new SentinelBlockRequestHandler());
        log.info("结束注册Sentinel处理器");
    }
}