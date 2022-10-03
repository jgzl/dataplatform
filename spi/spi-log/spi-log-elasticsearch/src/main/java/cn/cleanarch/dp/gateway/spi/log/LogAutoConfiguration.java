package cn.cleanarch.dp.gateway.spi.log;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@ComponentScan
@EnableElasticsearchRepositories
public class LogAutoConfiguration {

}
