package cn.cleanarch.dp.common.oauth.service;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.oauth.vo.LoginUser;
import cn.cleanarch.dp.system.sys.dto.SysUserInfoDTO;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author li7hai26@outlook.com
 * @date 2021/12/21
 */
public interface DpUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userdetails
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(SysUserInfoDTO info) {
		if (info == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		Set<String> dbAuthSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			dbAuthSet.addAll(info.getRoles());
			// 获取资源
			dbAuthSet.addAll(info.getPermissions());
		}
		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthSet.toArray(new String[0]));
		boolean enabled = StrUtil.equals(info.getLockFlag(), CommonConstants.STATUS_NORMAL+"");
		// 构造security用户
		return new LoginUser(info.getId(), info.getDeptId(), info.getMobile(), info.getAvatar(),
				info.getUserName(), info.getNickName(), SecurityConstants.BCRYPT + info.getPassword(), enabled, true, true,
				!CommonConstants.STATUS_LOCK.equals(info.getLockFlag()), authorities);
	}

	/**
	 * 通过用户实体查询
	 * @param pigUser user
	 * @return
	 */
	default UserDetails loadUserByUser(LoginUser pigUser) {
		return this.loadUserByUsername(pigUser.getUsername());
	}

}
