package cn.cleanarch.dp.gateway.admin.gateway.configuration;

import org.springframework.context.ApplicationEvent;

/**
 * @author li7hai26@gmail.com
 * @date 2021/11/5
 * <p>
 * 路由初始化事件
 */
public class DynamicRouteInitEvent extends ApplicationEvent {

    public DynamicRouteInitEvent(Object source) {
        super(source);
    }

}
