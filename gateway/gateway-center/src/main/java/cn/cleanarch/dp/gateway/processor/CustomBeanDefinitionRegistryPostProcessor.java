package cn.cleanarch.dp.gateway.processor;

import cn.cleanarch.dp.gateway.filter.CustomWeightCalculatorWebFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @Description 对指定WeightCalculatorWebFilter过滤器进行潜换，使用CustomWeightCalculatorWebFilter潜换方案
 * @Author JL
 * @Date 2021/10/12
 * @Version V1.0
 */
@Component
public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String BEAN_NAME = "weightCalculatorWebFilter";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 查找同名的bean，如果存在则用自定义bean覆盖
        RootBeanDefinition beanDefinition = (RootBeanDefinition) registry.getBeanDefinition(BEAN_NAME);
        if (beanDefinition != null) {
            registry.removeBeanDefinition(BEAN_NAME);
            beanDefinition = new RootBeanDefinition(CustomWeightCalculatorWebFilter.class);
            registry.registerBeanDefinition(BEAN_NAME, beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
