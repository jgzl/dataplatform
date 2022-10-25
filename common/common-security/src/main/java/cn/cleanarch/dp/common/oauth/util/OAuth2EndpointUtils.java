package cn.cleanarch.dp.common.oauth.util;

import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.WebmvcUtil;
import cn.cleanarch.dp.common.oauth.vo.LoginUser;
import cn.cleanarch.dp.system.vo.user.LoginUserVO;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author li7hai26@outlook.com
 * @description OAuth2 端点工具
 */
@UtilityClass
public class OAuth2EndpointUtils {

	public final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

	public MultiValueMap<String, String> getParameters(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
		parameterMap.forEach((key, values) -> {
			if (values.length > 0) {
				for (String value : values) {
					parameters.add(key, value);
				}
			}
		});
		return parameters;
	}

	public boolean matchesPkceTokenRequest(HttpServletRequest request) {
		return AuthorizationGrantType.AUTHORIZATION_CODE.getValue()
				.equals(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))
				&& request.getParameter(OAuth2ParameterNames.CODE) != null
				&& request.getParameter(PkceParameterNames.CODE_VERIFIER) != null;
	}

	public void throwError(String errorCode, String parameterName, String errorUri) {
		OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
		throw new OAuth2AuthenticationException(error);
	}

	/**
	 * 格式化输出token 信息
	 * @param auth 用户认证信息
	 * @param claims 扩展信息
	 * @return
	 * @throws IOException
	 */
	public void sendAccessTokenResponse(HttpServletResponse response, OAuth2Authorization auth, Map<String, Object> claims) {
		OAuth2AccessToken accessToken = auth.getAccessToken().getToken();
		OAuth2RefreshToken refreshToken = auth.getRefreshToken().getToken();
		LoginUserVO result = OAuth2EndpointUtils.getLoginUserVO(accessToken, refreshToken, claims);
		// 无状态 注意删除 context 上下文的信息
		SecurityContextHolder.clearContext();
		WebmvcUtil.okOut(response, R.success(result));
	}

	@NotNull
	public static LoginUserVO getLoginUserVO(OAuth2AccessToken accessToken, OAuth2RefreshToken refreshToken, Map<String, Object> additionalParameters) {
		LoginUserVO result = new LoginUserVO();
		result.setToken(accessToken.getTokenValue());
		List<String> roles = Lists.newArrayList();
		List<String> permissions = Lists.newArrayList();
		result.setRoles(roles);
		result.setPermissions(permissions);
		if (!CollectionUtils.isEmpty(additionalParameters)) {
			LoginUser user = (LoginUser) additionalParameters.get(SecurityConstants.DETAILS_USER_INFO);
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
