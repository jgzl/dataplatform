/*
 *
 *      Copyright (c) 2018-2025, li7hai26@outlook.com All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: li7hai26@outlook.com (wangiegie@gmail.com)
 *
 */

package cn.cleanarch.dp.system.feign;

import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.core.constant.ServiceNameConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.system.dataobject.oauth.SysOauthClientDetailsDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author li7hai26@outlook.com
 * @date 2020/12/05
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.GATEWAY_ADMIN_SERVICE)
public interface RemoteClientDetailsService {

	/**
	 * 通过clientId 查询客户端信息
	 * @param clientId 用户名
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/client/getClientDetailsById/{clientId}")
	R<SysOauthClientDetailsDO> getClientDetailsById(@PathVariable("clientId") String clientId,
			@RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 查询全部客户端
	 * @param from 调用标识
	 * @return R
	 */
	@GetMapping("/client/list")
	R<List<SysOauthClientDetailsDO>> listClientDetails(@RequestHeader(SecurityConstants.FROM) String from);

}
