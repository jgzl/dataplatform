package cn.cleanarch.dp.common.gateway.annotation;

import cn.cleanarch.dp.common.gateway.configuration.DynamicGatewayAccessAutoConfiguration;
import cn.cleanarch.dp.common.gateway.configuration.DynamicGatewayRouteConfAutoConfiguration;
import cn.cleanarch.dp.common.gateway.configuration.GatewayExceptionHandlerConfiguration;
import cn.cleanarch.dp.common.gateway.support.RedisRouteDefinitionWriter;
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
        DynamicGatewayAccessAutoConfiguration.class,
        GatewayExceptionHandlerConfiguration.class,
        RedisRouteDefinitionWriter.class,
})
public @interface EnableDynamicRoute {

}
