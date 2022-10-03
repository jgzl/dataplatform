package cn.cleanarch.dp.common.security.configuration;

import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.security.filter.TokenAuthenticationFilter;
import cn.cleanarch.dp.common.security.filter.TokenLoginFilter;
import cn.cleanarch.dp.common.security.handler.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


/**
 * 安全认证配置累
 * 启用方法级别的权限认证
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PermitAllUrlResolver permitAllUrlResolver;

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler() {
        return new LoginAuthenticationSuccessHandler();
    }

    @Bean
    public LoginAuthenticationFailureHandler loginAuthenticationFailureHandler() {
        return new LoginAuthenticationFailureHandler();
    }

    @Bean
    public LoginAuthenticationEntryPoint loginAuthenticationEntryPoint() {
        return new LoginAuthenticationEntryPoint();
    }

    @Bean
    public LoginAccessDeniedHandler loginAccessDeniedHandler() {
        return new LoginAccessDeniedHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LoginLogoutSuccessHandler();
    }

    /**
     * 不拦截静态资源
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/index.html", "/favicon.ico", "/css/**", "/js/**", "/images/**", "/fonts/**", "/error", "/webjars/**");
    }

    // 核心配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        permitAllUrlResolver.registry(http.authorizeRequests());
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 基于token，所以不需要session
                .and()
                .formLogin() //关闭登录
                .loginProcessingUrl(SecurityConstants.LOGIN_PATH)
                .permitAll()
                .and()
                .authorizeRequests()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(loginAuthenticationEntryPoint())
                .accessDeniedHandler(loginAccessDeniedHandler());

        // 开启登录认证流程过滤器
        TokenLoginFilter tokenLoginFilter = new TokenLoginFilter(authenticationManager());
        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(authenticationManager(), permitAllUrlResolver);
        tokenLoginFilter.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler());
        tokenLoginFilter.setAuthenticationFailureHandler(loginAuthenticationFailureHandler());
        http.addFilterAt(tokenLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(tokenAuthenticationFilter, BasicAuthenticationFilter.class);
        // 退出登录处理器
        // http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        http.logout().logoutUrl(SecurityConstants.LOGOUT_PATH).logoutSuccessHandler(logoutSuccessHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
