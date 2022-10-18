package cn.cleanarch.dp.common.oauth.vo;

import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends User {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 用户ID
     */
    @Getter
    private String userId;

    /**
     * 部门ID
     */
    @Getter
    private String deptId;

    /**
     * 别名
     */
    @Getter
    private String nickName;

    /**
     * 手机号
     */
    @Getter
    private String mobile;

    /**
     * 头像
     */
    @Getter
    private String avatar;

    public LoginUser(){
        super("","", Lists.newArrayList());
    }

    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username,password,authorities);
    }

    public LoginUser(String userId, String deptId, String mobile, String avatar,
                     String userName, String nickName, String password, boolean enabled,
                     boolean accountNonExpired,
                     boolean credentialsNonExpired,
                     boolean accountNonLocked,
                     Collection<? extends GrantedAuthority> authorities) {
        super(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.deptId = deptId;
        this.nickName = nickName;
        this.mobile = mobile;
        this.avatar = avatar;
    }

    public boolean isAdmin() {
        return isAdmin(this.getUsername());
    }

    public static boolean isAdmin(String username) {
        return ObjectUtil.equal(username, SecurityConstants.DEFAULT_USER);
    }
}