package cn.cleanarch.dp.common.mq.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProducerService {

    private final Map<String, KafkaTemplate<String, String>> kafkaTemplateMap;

    public void sendMessage(String clusterName, String topic, String message) {
        KafkaTemplate<String, String> kafkaTemplate = kafkaTemplateMap.get(clusterName);
        if (kafkaTemplate != null) {
            kafkaTemplate.send(topic, message);
        } else {
            throw new RuntimeException("No Kafka template found for cluster " + clusterName);
        }
    }

    public void sendMessage(String clusterName, String topic, String key, String message) {
        KafkaTemplate<String, String> kafkaTemplate = kafkaTemplateMap.get(clusterName);
        if (kafkaTemplate != null) {
            kafkaTemplate.send(topic, key, message);
        } else {
            throw new RuntimeException("No Kafka template found for cluster " + clusterName);
        }
    }

    public void sendMessage(String clusterName, String topic, Integer partition, String key, String message) {
        KafkaTemplate<String, String> kafkaTemplate = kafkaTemplateMap.get(clusterName);
        if (kafkaTemplate != null) {
            kafkaTemplate.send(topic, partition, key, message);
        } else {
            throw new RuntimeException("No Kafka template found for cluster " + clusterName);
        }
    }
}