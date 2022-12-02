package cn.cleanarch.dp.tool.third;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Slf4j
@SpringBootTest
class AmapClientTest {
    @Autowired
    private AmapClient amapClient;
    @Test
    void getLocation() {
        // 调用接口
        Map result = amapClient.getLocation("121.475078", "31.223577");
        log.info("调用结果为:{}",result);
    }
}