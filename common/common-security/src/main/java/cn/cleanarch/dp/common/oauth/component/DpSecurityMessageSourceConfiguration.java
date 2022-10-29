package cn.cleanarch.dp.common.oauth.component;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * @author li7hai26@outlook.com
 * @date 2022-06-04
 * <p>
 * 注入自定义错误处理,覆盖 org/springframework/security/messages 内置异常
 */
@ConditionalOnWebApplication(type = SERVLET)
public class DpSecurityMessageSourceConfiguration implements WebMvcConfigurer {

	@Bean
	public MessageSource securityMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.addBasenames("classpath:i18n/errors/messages");
		messageSource.setDefaultLocale(Locale.CHINA);
		return messageSource;
	}

}
