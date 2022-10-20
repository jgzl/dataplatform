package cn.cleanarch.dp.common.oauth.util;

import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.oauth.vo.LoginUser;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lihaifeng
 * @version 1.0
 * @title: AppContextHolder
 * @description: 应用上下文
 * @date: 2022/5/30 20:55
 */
@UtilityClass
public class AppContextHolder {

    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     *
     * @param authentication
     * @return ExtendUser
     * <p>
     */
    public LoginUser getUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUser) {
            return (LoginUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public LoginUser getUser() {
        Authentication authentication = getAuthentication();
        return getUser(authentication);
    }

    /**
     * 获取用户角色信息
     *
     * @return 角色集合
     */
    public List<String> getRoles() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return Lists.newArrayList();
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roleList = new ArrayList<>();
        authorities.stream().filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE_PREFIX))
                .forEach(granted -> roleList.add(granted.getAuthority()));
        return roleList;
    }


    /**
     * 获取用户角色信息
     *
     * @return 角色集合
     */
    public List<String> getPermissions() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return Lists.newArrayList();
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roleList = new ArrayList<>();
        authorities.stream().filter(granted -> !StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE_PREFIX))
                .forEach(granted -> roleList.add(granted.getAuthority()));
        return roleList;
    }

    public static boolean isAdmin() {
        LoginUser user = getUser();
        if (user != null){
            return user.isAdmin();
        }
        return false;
    }
}
