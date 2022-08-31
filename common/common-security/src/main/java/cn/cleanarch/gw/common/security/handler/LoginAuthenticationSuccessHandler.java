package cn.cleanarch.gw.common.security.handler;

import cn.cleanarch.gw.common.core.constant.SecurityConstants;
import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.core.utils.JwtTokenUtil;
import cn.cleanarch.gw.common.core.utils.WebmvcUtil;
import cn.cleanarch.gw.common.model.system.vo.LoginUserInfo;
import cn.cleanarch.gw.common.model.system.vo.LoginUserVo;
import cn.cleanarch.gw.common.security.vo.LoginUser;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser user = (LoginUser) authentication.getPrincipal();
        Map<String, Object> kvMap = Maps.newHashMap();
        kvMap.put(SecurityConstants.DETAILS_USER_ID, user.getUserId());
        kvMap.put(SecurityConstants.DETAILS_USERNAME, user.getUsername());
        String token = JwtTokenUtil.createToken(kvMap);
        LoginUserVo result = new LoginUserVo();
        result.setToken(token);
        List<String> roles = Lists.newArrayList();
        List<String> permissions = Lists.newArrayList();
        result.setRoles(roles);
        result.setPermissions(permissions);
        result.setUserId(user.getUserId());
        result.setUsername(user.getUsername());
        result.setNickname(user.getNickName());

        LoginUserInfo userInfo = new LoginUserInfo();
        userInfo.setRole(roles);
        userInfo.setUserId(user.getUserId()+"");
        userInfo.setUserName(user.getUsername());
        result.setUserInfo(userInfo);

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
        WebmvcUtil.okOut(response, R.success(result));
    }
}
