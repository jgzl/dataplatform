package cn.cleanarch.dp.system.feign;

import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.core.constant.ServiceNameConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.system.dto.SysUserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author li7hai26@outlook.com
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserService {

	/**
	 * 通过用户名查询用户、角色信息
	 * @param username 用户名
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/system/user/info/{username}")
	R<SysUserInfoDTO> info(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过手机号码查询用户、角色信息
	 * @param phone 手机号码
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/system/user/app/info/{phone}")
	R<SysUserInfoDTO> infoByMobile(@PathVariable("phone") String phone, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 根据部门id，查询对应的用户 id 集合
	 * @param deptIds 部门id 集合
	 * @param from 调用标志
	 * @return 用户 id 集合
	 */
	@GetMapping("/system/user/ids")
	R<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds,
			@RequestHeader(SecurityConstants.FROM) String from);

}
