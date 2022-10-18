package cn.cleanarch.dp.common.oauth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.Filter;

/**
 * @author lihaifeng
 * @date 2022/01/11
 */
@Configuration
public class CorsConfiguration {
    private static final Long MAX_AGE = 18000L;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Filter corsFilter() {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许
        config.addAllowedOriginPattern("*");
        // #允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 允许提交请求的方法类型，*表示全部允许
        config.addAllowedMethod("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(MAX_AGE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
