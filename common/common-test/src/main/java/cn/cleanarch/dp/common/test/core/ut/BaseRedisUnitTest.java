package cn.cleanarch.dp.common.test.core.ut;

import cn.cleanarch.dp.common.redis.template.ReactiveRedisTemplateConfiguration;
import cn.cleanarch.dp.common.redis.template.RedisTemplateConfiguration;
import cn.cleanarch.dp.common.test.config.MockRedisServerConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * 依赖内存 Redis 的单元测试
 * <p>
 * 相比 {@link BaseDbUnitTest} 来说，从内存 DB 改成了内存 Redis
 *
 * @author 整洁架构
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = BaseRedisUnitTest.Application.class)
@ActiveProfiles("unit-test") // 设置使用 application-unit-test 配置文件
public class BaseRedisUnitTest {

    @Import({
            // Redis 配置类
            MockRedisServerConfiguration.class, // Redis 测试配置类，用于启动 RedisServer
            RedisAutoConfiguration.class, // Spring Redis 自动配置类
            RedisTemplateConfiguration.class, // 自己的 Redis 配置类
            ReactiveRedisTemplateConfiguration.class, // 自己的 Redis 配置类
    })
    public static class Application {
    }

}
