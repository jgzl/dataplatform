package cn.cleanarch.dp.common.mq.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * This class demonstrates how to use the configuration options in `config.yml`.
 *
 * <p>For more information on the YAML configuration file, see `resources/config.yml`.</p>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = KafkaClusterProperties.KAFKA_CLUSTER_PROPERTIES_PREFIX)
public class KafkaClusterProperties {

    public static final String KAFKA_CLUSTER_PROPERTIES_PREFIX = "kafka";

    private Map<String, Config> clusters;

    @Data
    public static class Config {

        private boolean enabled = true;

        private Map<String, String> properties = new HashMap<>();

        private Map<String, String> producer = new HashMap<>();

        private Map<String, String> consumer = new HashMap<>();

    }
}