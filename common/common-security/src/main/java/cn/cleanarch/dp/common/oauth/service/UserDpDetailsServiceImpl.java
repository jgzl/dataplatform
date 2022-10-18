package cn.cleanarch.dp.common.oauth.service;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.oauth.vo.LoginUser;
import cn.cleanarch.dp.system.dto.SysUserInfoDTO;
import cn.cleanarch.dp.system.feign.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author li7hai26@outlook.com hccake
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class UserDpDetailsServiceImpl implements DpUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(username) != null) {
			return (LoginUser) cache.get(username).get();
		}

		R<SysUserInfoDTO> result = remoteUserService.info(username, SecurityConstants.FROM_IN);
		UserDetails userDetails = getUserDetails(result.getData());
		if (cache != null) {
			cache.put(username, userDetails);
		}
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
