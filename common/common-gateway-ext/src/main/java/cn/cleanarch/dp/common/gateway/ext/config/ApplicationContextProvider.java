package cn.cleanarch.dp.common.gateway.ext.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description 获取容器中的ApplicationContext上下文对象
 * @Author jianglong
 * @Date 2020/05/07
 * @Version V1.0
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 通过类型获取容器中的bean
     * @param zlass
     * @return
     */
    public Object getBean(Class zlass){
        return applicationContext.getBean(zlass);
    }

    /**
     * 通过名称获取容器中的bean
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }
}
