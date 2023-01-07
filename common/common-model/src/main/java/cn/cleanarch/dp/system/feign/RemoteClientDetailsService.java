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
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteClientDetailsService {

	/**
	 * 通过clientId 查询客户端信息
	 * @param clientId 用户名
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/system/client/getClientDetailsById/{clientId}")
	R<SysOauthClientDetailsDO> getClientDetailsById(@PathVariable("clientId") String clientId,
			@RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 查询全部客户端
	 * @param from 调用标识
	 * @return R
	 */
	@GetMapping("/system/client/list")
	R<List<SysOauthClientDetailsDO>> listClientDetails(@RequestHeader(SecurityConstants.FROM) String from);

}
