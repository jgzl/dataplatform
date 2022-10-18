/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
