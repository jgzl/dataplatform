package cn.cleanarch.dp.auth.support.handler;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.WebmvcUtil;
import cn.cleanarch.dp.common.oauth.util.OAuth2EndpointUtils;
import cn.cleanarch.dp.system.vo.user.LoginUserVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @see cn.cleanarch.dp.auth.support.CustomOAAuth2AccessTokenGenerator#generate
 * @author li7hai26@outlook.com
 * @date 2022-06-02
 */
@Slf4j
public class InfraAuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {

	private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

	/**
	 * Called when a user has been successfully authenticated.
	 * @param request the request which caused the successful authentication
	 * @param response the response
	 * @param authentication the <tt>Authentication</tt> object which was created during
	 * the authentication process.
	 */
	@SneakyThrows
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
//		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
//		Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
//		if (MapUtil.isNotEmpty(map)) {
//			// 发送异步日志事件
//			PigUser userInfo = (PigUser) map.get(SecurityConstants.DETAILS_USER);
//			log.info("用户：{} 登录成功", userInfo.getName());
//			SecurityContextHolder.getContext().setAuthentication(accessTokenAuthentication);
//			SysLog logVo = SysLogUtils.getSysLog();
//			logVo.setTitle("登录成功");
//			String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
//			if (StrUtil.isNotBlank(startTimeStr)) {
//				Long startTime = Long.parseLong(startTimeStr);
//				Long endTime = System.currentTimeMillis();
//				logVo.setTime(endTime - startTime);
//			}
//			logVo.setCreateBy(userInfo.getName());
//			logVo.setUpdateBy(userInfo.getName());
//			SpringUtil.publishEvent(new SysLogEvent(logVo));
//		}

		// 输出token
		sendAccessTokenResponse(response, authentication);
	}

	private void sendAccessTokenResponse(HttpServletResponse response,Authentication authentication) throws IOException {
		OAuth2AccessTokenAuthenticationToken auth = (OAuth2AccessTokenAuthenticationToken) authentication;
		Map<String, Object> claims = auth.getAdditionalParameters();
		OAuth2AccessToken accessToken = auth.getAccessToken();
		OAuth2RefreshToken refreshToken = auth.getRefreshToken();
		LoginUserVO result = OAuth2EndpointUtils.getLoginUserVO(accessToken, refreshToken, claims);
		// 无状态 注意删除 context 上下文的信息
		SecurityContextHolder.clearContext();
		WebmvcUtil.okOut(response, R.success(result));
	}

}
