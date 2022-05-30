package cn.cleanarch.gw.common.test.config;

import com.github.microwww.redis.RedisServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Lazy(value = false) // 禁止延迟加载
public class MockRedisServerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MockRedisServerConfiguration.class);
    private static RedisServer server; // 运行多个单元测试的时候, 会启动多个 ApplicationContext, 导致多次端口绑定, 所以此处缓存
    private static final AtomicInteger count = new AtomicInteger(0);

    @Bean(destroyMethod = "close")
    public RedisServer mockRedisServer(RedisProperties redisProperties) throws IOException {
        if (server == null) synchronized (MockRedisServerConfiguration.class) {
            if (server == null) {
                RedisServer server = new RedisServer() {
                    @Override
                    public void close() throws IOException {
                        if (count.decrementAndGet() <= 0) {
                            logger.info("Mocker Redis will STOP !");
                            super.close();
                        }
                    }
                };
                server.listener(redisProperties.getHost(), redisProperties.getPort());
                MockRedisServerConfiguration.server = server;
            }
        }
        count.incrementAndGet();
        logger.info("Mocker Redis start :: [{}:{}], set 'server.redis.host' to match it", redisProperties.getHost(), redisProperties.getPort());
        return server;
    }

}
