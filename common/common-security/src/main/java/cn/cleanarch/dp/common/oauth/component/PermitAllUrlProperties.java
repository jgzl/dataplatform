package cn.cleanarch.dp.common.oauth.component;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.oauth.annotation.Inner;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author li7hai26@outlook.com
 * @date 2020-03-11
 * <p>
 * 资源服务器对外直接暴露URL,如果设置context-path 要特殊处理
 */
@Slf4j
@ConfigurationProperties(prefix = PermitAllUrlProperties.PREFIX)
public class PermitAllUrlProperties implements InitializingBean {

    public static final String PREFIX = CommonConstants.CONFIGURATION_PREFIX + ".oauth2.ignore";

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private static final Set<String> DEFAULT_IGNORE_URLS = CollUtil.newHashSet(
            CommonConstants.ACTUATOR_URL_PATTERN, "/error", "/v3/api-docs", CommonConstants.HEART_BEAT_URL);

    @Getter
    @Setter
    private Set<String> urls = new HashSet<>();

    @Override
    public void afterPropertiesSet() {
        urls.addAll(DEFAULT_IGNORE_URLS);
        RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = map.get(info);

            // 获取方法上边的注解 替代path variable 为 *
            Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
            Optional.ofNullable(method).ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
                    .getPatternValues().forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));

            // 获取类上边的注解, 替代path variable 为 *
            Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
            Optional.ofNullable(controller).ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
                    .getPatternValues().forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
        });
    }

}
