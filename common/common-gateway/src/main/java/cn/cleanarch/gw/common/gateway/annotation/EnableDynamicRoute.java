package cn.cleanarch.gw.common.gateway.annotation;

import cn.cleanarch.gw.common.gateway.configuration.DynamicGatewayAccessConfAutoConfiguration;
import cn.cleanarch.gw.common.gateway.configuration.DynamicGatewayRouteConfAutoConfiguration;
import cn.cleanarch.gw.common.gateway.configuration.GatewayExceptionHandlerConfiguration;
import cn.cleanarch.gw.common.gateway.support.RedisRouteDefinitionWriter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author li7hai26@gmail.com
 * @date 2021/11/5
 * <p>
 * 开启动态路由
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        DynamicGatewayRouteConfAutoConfiguration.class,
        DynamicGatewayAccessConfAutoConfiguration.class,
        GatewayExceptionHandlerConfiguration.class,
        RedisRouteDefinitionWriter.class,
})
public @interface EnableDynamicRoute {

}
