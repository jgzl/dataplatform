package cn.cleanarch.dp.common.security.handler;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.WebmvcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Slf4j
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("登录认证发生异常:", e);
        WebmvcUtil.errorOut(response, R.error("登录异常", e.getMessage()));
    }
}
