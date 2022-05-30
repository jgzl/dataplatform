package cn.cleanarch.gw.common.security.filter;

import cn.cleanarch.gw.common.core.constant.SecurityConstants;
import cn.cleanarch.gw.common.core.utils.JacksonUtil;
import cn.cleanarch.gw.common.model.system.vo.LoginUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * token登陆模块
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SecurityConstants.LOGIN_PATH, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        // 用以支持json登录
        if (req.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || req.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = req.getInputStream()) {
                LoginUserVo authenticationBean = JacksonUtil.readValue(is, LoginUserVo.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
            } catch (IOException e) {
                log.error("IO发生异常:", e);
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            } finally {
                setDetails(req, authRequest);
            }
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(req, res);
        }
    }

}
