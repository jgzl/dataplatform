package cn.cleanarch.dp.common.mq.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @KafkaListener(id = "primary", topics = "test-topic",
            containerFactory = "primaryKafkaListenerContainerFactory")
    public void consumeFromPrimary(String message) {
        System.out.println("[primary] Received message: " + message);
    }

    @KafkaListener(id = "secondary", topics = "test-topic",
            containerFactory = "secondaryKafkaListenerContainerFactory")
    public void consumeFromSecondary(String message) {
        System.out.println("[secondary] Received message: " + message);
    }

    @KafkaListener(id = "tertiary", topics = "test-topic",
            containerFactory = "tertiaryKafkaListenerContainerFactory")
    public void consumeFromTertiary(String message) {
        System.out.println("[tertiary] Received message: " + message);
    }
}