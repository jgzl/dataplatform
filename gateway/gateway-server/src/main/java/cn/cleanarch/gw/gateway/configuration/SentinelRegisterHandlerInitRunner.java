package cn.cleanarch.gw.gateway.configuration;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * 注册处理器
 * @author vlfac
 */
@Slf4j
@Configuration
public class SentinelRegisterHandlerInitRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("开始注册Sentinel处理器");
        GatewayCallbackManager.setBlockHandler(new SentinelBlockRequestHandler());
        log.info("结束注册Sentinel处理器");
    }
}