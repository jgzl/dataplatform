package cn.cleanarch.dp.common.mq.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class KafkaClusterPropertiesTest {
    @Autowired
    private KafkaClusterProperties kafkaClusterProperties;
    @Test
    public void validKafkaPropertiesSuccess() {
        Map<String, KafkaClusterProperties.Config> clusters = kafkaClusterProperties.getClusters();
        KafkaClusterProperties.Config config = clusters.get("primary");
        Map<String, String> properties = config.getProperties();
        Map<String, String> producer = config.getProducer();
        Map<String, String> consumer = config.getConsumer();
    }
}