package cn.cleanarch.dp.common.test.core.ut;

import cn.cleanarch.dp.common.data.mybatis.MybatisPlusConfiguration;
import cn.cleanarch.dp.common.redis.template.ReactiveRedisTemplateConfiguration;
import cn.cleanarch.dp.common.redis.template.RedisTemplateConfiguration;
import cn.cleanarch.dp.common.test.config.MockRedisServerConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

/**
 * 依赖内存 DB + Redis 的单元测试
 * <p>
 * 相比 {@link BaseDbUnitTest} 来说，额外增加了内存 Redis
 *
 * @author 整洁架构
 */
@SpringBootTest(classes = BaseDbAndRedisUnitTest.Application.class)
@ActiveProfiles("unit-test") // 设置使用 application-unit-test 配置文件
@Sql(scripts = "classpath:/sql/create_tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
// 每个单元测试结束后，清理 DB
@Sql(scripts = "classpath:/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // 每个单元测试结束后，清理 DB
public class BaseDbAndRedisUnitTest {

    @Import({
            // DB 配置类
            DataSourceAutoConfiguration.class, // Spring DB 自动配置类
            DataSourceTransactionManagerAutoConfiguration.class, // Spring 事务自动配置类
            // MyBatis 配置类
            MybatisPlusConfiguration.class, // 自己的 MyBatis 配置类
            MybatisPlusAutoConfiguration.class, // MyBatis 的自动配置类

            // Redis 配置类
            MockRedisServerConfiguration.class, // Redis 测试配置类，用于启动 RedisServer
            RedisAutoConfiguration.class, // Spring Redis 自动配置类
            RedisTemplateConfiguration.class, // 自己的 Redis 配置类
            ReactiveRedisTemplateConfiguration.class, // 自己的 Redis 配置类
    })
    public static class Application {
    }

}
