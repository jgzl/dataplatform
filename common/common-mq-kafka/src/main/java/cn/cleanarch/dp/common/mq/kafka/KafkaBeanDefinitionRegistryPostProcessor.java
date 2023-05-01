package cn.cleanarch.dp.common.mq.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 此处的 @EnableConfigurationProperties(value = KafkaClusterProperties.class) 是为了其他地方能够方便的引入配置,
 * 在当前bean定义注册处理器中，有且只能够通过EnvironmentAware来获取配置信息，注解引入的处理必须通过对应的BeanPostProcessor来处理
 * @author lihaifeng
 */
@EnableConfigurationProperties(value = KafkaClusterProperties.class)
@Component
public class KafkaBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private KafkaClusterProperties kafkaClusterProperties;

    public void registerKafkaBeans(BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, KafkaClusterProperties.Config> clusterConfigs = kafkaClusterProperties.getClusters();
        Assert.notEmpty(clusterConfigs,"kafka.clusters下配置不允许为空");

        for (String clusterName : clusterConfigs.keySet()) {
            KafkaClusterProperties.Config config = clusterConfigs.get(clusterName);
            if (config.isEnabled()) {
                Map<String, String> kafkaConfig = config.getProperties();
                Map<String, String> producerProperties = config.getProducer();
                Map<String, String> consumerProperties = config.getConsumer();
                kafkaConfig.forEach((key,value)->producerProperties.merge(key,value,(v1,v2)->v2));
                kafkaConfig.forEach((key,value)->consumerProperties.merge(key,value,(v1,v2)->v2));

                // Instantiate Kafka producer factory
                DefaultKafkaProducerFactory<String,String> kafkaProducerFactory =
                        new DefaultKafkaProducerFactory<>(new HashMap<>(producerProperties),
                        new StringSerializer(),
                        new StringSerializer());
                // Instantiate Kafka template
                KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
                // Instantiate Kafka consumer factory
                DefaultKafkaConsumerFactory<String, String> kafkaConsumerFactory =
                        new DefaultKafkaConsumerFactory<>(new HashMap<>(consumerProperties),
                                new StringDeserializer(),
                                new StringDeserializer());

                // Create Kafka listener container factory
                ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory =
                        new ConcurrentKafkaListenerContainerFactory<>();
                kafkaListenerContainerFactory.setConsumerFactory(kafkaConsumerFactory);
                kafkaListenerContainerFactory.setConcurrency(1);

                // Register Kafka listener container factory and Kafka template beans
                registerBean(beanDefinitionRegistry, clusterName + "KafkaListenerContainerFactory", kafkaListenerContainerFactory);
                registerBean(beanDefinitionRegistry, clusterName + "KafkaTemplate", kafkaTemplate);
            }
        }
    }

    private void registerBean(BeanDefinitionRegistry beanDefinitionRegistry, String name, Object bean) {
        BeanDefinition beanDefinition = new RootBeanDefinition(bean.getClass());
        BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition, name);
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, beanDefinitionRegistry);
        ((DefaultListableBeanFactory) beanDefinitionRegistry).registerSingleton(name,bean);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registerKafkaBeans(registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.kafkaClusterProperties = Binder.get(environment).bind(KafkaClusterProperties.KAFKA_CLUSTER_PROPERTIES_PREFIX,KafkaClusterProperties.class).get();
    }
}