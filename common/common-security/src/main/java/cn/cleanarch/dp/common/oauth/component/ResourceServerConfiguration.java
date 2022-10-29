package cn.cleanarch.dp.common.oauth.component;

import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author li7hai26@outlook.com
 * @date 2022-06-04
 *
 * 资源服务器认证授权配置
 */
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class ResourceServerConfiguration {

	protected final ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

	private final PermitAllUrlProperties permitAllUrl;

	private final DpBearerTokenExtractor dpBearerTokenExtractor;

	private final OpaqueTokenIntrospector customOpaqueTokenIntrospector;

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers(ArrayUtil.toArray(permitAllUrl.getUrls(), String.class)).permitAll().anyRequest()
				.authenticated())
				.oauth2ResourceServer(
						oauth2 -> oauth2.opaqueToken(token -> token.introspector(customOpaqueTokenIntrospector))
								.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
								.bearerTokenResolver(dpBearerTokenExtractor))
				.headers().frameOptions().disable().and().csrf().disable();

		return http.build();
	}

}
