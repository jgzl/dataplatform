package cn.cleanarch.dp.common.security.handler;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.JacksonUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
public class LoginLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        ServletUtil.write(response, JacksonUtil.toJsonString(R.success("登出成功")), ContentType.JSON.getValue());
    }
}
