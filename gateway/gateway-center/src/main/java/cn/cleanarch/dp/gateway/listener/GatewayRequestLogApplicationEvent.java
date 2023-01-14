package cn.cleanarch.dp.gateway.listener;

import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import org.springframework.context.ApplicationEvent;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
public class GatewayRequestLogApplicationEvent extends ApplicationEvent {

    private final GatewayLogDO gatewayLog;

    public GatewayRequestLogApplicationEvent(Object source, GatewayLogDO event) {
        super(source);
        this.gatewayLog = event;
    }

    public GatewayLogDO getGatewayLog() {
        return this.gatewayLog;
    }

}
