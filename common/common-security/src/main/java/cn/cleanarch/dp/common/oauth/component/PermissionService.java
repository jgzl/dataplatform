
package cn.cleanarch.dp.common.oauth.component;

import cn.cleanarch.dp.common.oauth.util.AppContextHolder;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.util.PatternMatchUtils;

/**
 * @author li7hai26@outlook.com
 * @date 2019/2/1 接口权限判断工具
 */
public class PermissionService {

	/**
	 * 判断接口是否有任意xxx，xxx权限
	 * @param permissions 权限
	 * @return {boolean}
	 */
	public boolean hasPermission(String... permissions) {
		if (ArrayUtil.isEmpty(permissions)) {
			return false;
		}
		return AppContextHolder.getPermissions().stream().anyMatch(permission->PatternMatchUtils.simpleMatch(permissions, permission));
	}

	/**
	 * 判断接口是否有任意xxx，xxx角色
	 * @param roles 角色
	 * @return {boolean}
	 */
	public boolean hasRole(String... roles) {
		// 如果为空，说明已经有权限
		if (ArrayUtil.isEmpty(roles)) {
			return true;
		}
		return AppContextHolder.getRoles().stream().anyMatch(role->PatternMatchUtils.simpleMatch(roles, role));
	}
}
