package cn.cleanarch.dp.gateway.fish.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Description 发布事件，触发监听事件的方法
 * @Author JL
 * @Date 2020/06/02
 * @Version V1.0
 */
@Lazy
@Slf4j
@Component
public class ApplicationEventPublisherFactory implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * 发布清除权重分组WeightRemoveApplicationEvent事件，会触发MyWeightCalculatorWebFilter过滤器监听并从权重集合中移除group
     * @param group
     */
    public void publisherWeightRemoveEvent(String group){
        this.publisher.publishEvent(new WeightRemoveApplicationEvent(this, group));
    }

}
