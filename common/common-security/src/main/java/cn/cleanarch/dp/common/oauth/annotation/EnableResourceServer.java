package cn.cleanarch.dp.common.oauth.annotation;

import cn.cleanarch.dp.common.oauth.component.ResourceServerAutoConfiguration;
import cn.cleanarch.dp.common.oauth.component.ResourceServerConfiguration;
import cn.cleanarch.dp.common.oauth.feign.FeignClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * @author li7hai26@outlook.com
 * @date 2022-06-04
 * <p>
 * 资源服务注解
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ResourceServerAutoConfiguration.class, ResourceServerConfiguration.class,
		FeignClientConfiguration.class })
public @interface EnableResourceServer {

}
