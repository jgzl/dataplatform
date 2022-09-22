package cn.cleanarch.gw.common.security.service;

import cn.cleanarch.gw.common.core.constant.CommonConstants;
import cn.cleanarch.gw.common.core.constant.SecurityConstants;
import cn.cleanarch.gw.common.security.vo.LoginUser;
import cn.cleanarch.gw.gateway.admin.system.dto.SysUserInfoDTO;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@RequiredArgsConstructor
@Service
public class LoginUserDetailsService implements UserDetailsService {

    private final SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserInfoDTO sysUserInfoDTO = userService.findUserInfo(username);
        UserDetails userDetails = getUserDetails(sysUserInfoDTO);
        return userDetails;
    }

    private UserDetails getUserDetails(SysUserInfoDTO info) {
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
}
