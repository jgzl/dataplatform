package cn.cleanarch.dp.auth.support.handler;

import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.WebmvcUtil;
import cn.cleanarch.dp.common.oauth.vo.LoginUser;
import cn.cleanarch.dp.system.vo.LoginUserVO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author li7hai26@outlook.com
 * @date 2022-06-02
 */
@Slf4j
public class PigAuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {

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
		sendAccessTokenResponse(request, response, authentication);
	}

	private void sendAccessTokenResponse(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

		OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
		OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

		LoginUserVO result = getLoginUserVO(accessToken, refreshToken, additionalParameters);

//		OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
//				.tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
//		if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
//			builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
//		}
//		if (refreshToken != null) {
//			builder.refreshToken(refreshToken.getTokenValue());
//			result.setRefreshToken(refreshToken.getTokenValue());
//		}
//		if (!CollectionUtils.isEmpty(additionalParameters)) {
//			builder.additionalParameters(additionalParameters);
//		}
//		OAuth2AccessTokenResponse accessTokenResponse = builder.build();


		// 无状态 注意删除 context 上下文的信息
		SecurityContextHolder.clearContext();
		WebmvcUtil.okOut(response, R.success(result));
	}

	@NotNull
	private static LoginUserVO getLoginUserVO(OAuth2AccessToken accessToken, OAuth2RefreshToken refreshToken, Map<String, Object> additionalParameters) {
		LoginUserVO result = new LoginUserVO();
		result.setToken(accessToken.getTokenValue());
		List<String> roles = Lists.newArrayList();
		List<String> permissions = Lists.newArrayList();
		result.setRoles(roles);
		result.setPermissions(permissions);
		if (!CollectionUtils.isEmpty(additionalParameters)) {
			String username = Convert.toStr(additionalParameters.get("sub"));
			LoginUser user = (LoginUser) additionalParameters.get(username);
			result.setUserId(user.getUserId());
			result.setUserName(user.getUsername());
			result.setNickName(user.getNickName());
			Collection<GrantedAuthority> authorities = user.getAuthorities();
			if (CollUtil.isNotEmpty(authorities)) {
				authorities.stream().map(GrantedAuthority::getAuthority).forEach(authority -> {
					if (authority.startsWith(SecurityConstants.ROLE_PREFIX)) {
						roles.add(authority);
					} else {
						permissions.add(authority);
					}
				});
			}
		}
		if (refreshToken != null) {
			result.setRefreshToken(refreshToken.getTokenValue());
		}
		return result;
	}

}
