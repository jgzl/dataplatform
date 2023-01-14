package cn.cleanarch.dp.common.oauth.service;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.oauth.vo.LoginUser;
import cn.cleanarch.dp.system.sys.dto.SysUserInfoDTO;
import cn.cleanarch.dp.system.sys.feign.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author li7hai26@outlook.com hccake
 */
@Slf4j
@RequiredArgsConstructor
public class AppDpUserDetailsServiceImpl implements DpUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 手机号登录
	 * @param phone 手机号
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(phone) != null) {
			return (LoginUser) cache.get(phone).get();
		}

		R<SysUserInfoDTO> result = remoteUserService.infoByMobile(phone, SecurityConstants.FROM_IN);

		UserDetails userDetails = getUserDetails(result.getData());
		if (cache != null) {
			cache.put(phone, userDetails);
		}
		return userDetails;
	}

	/**
	 * check-token 使用
	 * @param pigUser user
	 * @return
	 */
	@Override
	public UserDetails loadUserByUser(LoginUser pigUser) {
		return this.loadUserByUsername(pigUser.getMobile());
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.APP.equals(clientId);
	}

}
