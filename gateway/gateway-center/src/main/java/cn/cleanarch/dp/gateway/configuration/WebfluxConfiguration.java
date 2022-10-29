package cn.cleanarch.dp.gateway.configuration;

import cn.cleanarch.dp.gateway.common.FilterOrderConstants;
import cn.cleanarch.dp.gateway.configuration.properties.GatewayProperties;
import cn.cleanarch.dp.gateway.filter.webflux.FileSizeFilter;
import cn.cleanarch.dp.gateway.filter.webflux.GatewayApiAccessFilter;
import cn.cleanarch.dp.gateway.filter.webflux.GatewayApiLogFilter;
import cn.cleanarch.dp.gateway.filter.webflux.GatewayApiPrefixFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.stream.Collectors;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
@Configuration
@EnableConfigurationProperties(value = {
        GatewayProperties.class
})
@RequiredArgsConstructor
public class WebfluxConfiguration implements WebFluxConfigurer {

    private final GatewayProperties gatewayProperties;

    @Bean(FileSizeFilter.FILTER_NAME)
    public FileSizeFilter fileSizeFilter() {
        return new FileSizeFilter(gatewayProperties.getFile().getFileMaxSize());
    }

    @Order(FilterOrderConstants.GATEWAY_API_ACCESS_FILTER)
    @Bean(GatewayApiAccessFilter.FILTER_NAME)
    public GatewayApiAccessFilter gatewayApiAccessFilter() {
        return new GatewayApiAccessFilter(gatewayProperties);
    }

    @Order(FilterOrderConstants.GATEWAY_API_PREFIX_FILTER)
    @Bean(GatewayApiPrefixFilter.FILTER_NAME)
    public GatewayApiPrefixFilter gatewayApiPrefixFilter() {
        return new GatewayApiPrefixFilter(gatewayProperties);
    }

    @Order(FilterOrderConstants.GATEWAY_API_LOG_FILTER)
    @Bean(GatewayApiLogFilter.FILTER_NAME)
    public GatewayApiLogFilter gatewayApiLogFilter() {
        return new GatewayApiLogFilter(gatewayProperties);
    }

    /**
     * 报障通过feign调用时能够正常反序列化数据
     * @param converters
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}
