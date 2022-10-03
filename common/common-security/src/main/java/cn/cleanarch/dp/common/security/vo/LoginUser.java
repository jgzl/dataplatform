package cn.cleanarch.dp.common.security.vo;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends User {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Getter
    private final String userId;

    /**
     * 部门ID
     */
    @Getter
    private final String deptId;

    /**
     * 别名
     */
    @Getter
    private final String nickName;

    /**
     * 手机号
     */
    @Getter
    private final String mobile;

    /**
     * 头像
     */
    @Getter
    private final String avatar;

    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     *
     * @param userId                用户ID
     * @param deptId                部门ID
     * @param userName              the userName presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param password              the password that should be presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param enabled               set to <code>true</code> if the user is enabled
     * @param accountNonExpired     set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     *                              expired
     * @param accountNonLocked      set to <code>true</code> if the account is not locked
     * @param authorities           the authorities that should be granted to the caller if they
     *                              presented the correct userName and password and the user is enabled. Not null.
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     *                                  a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    @JsonCreator
    public LoginUser(@JsonProperty("userId") String userId, @JsonProperty("deptId") String deptId,
                     @JsonProperty("mobile") String mobile, @JsonProperty("avatar") String avatar,
                     @JsonProperty("userName") String userName, @JsonProperty("nickName") String nickName,
                     @JsonProperty("password") String password, @JsonProperty("enabled") boolean enabled,
                     @JsonProperty("accountNonExpired") boolean accountNonExpired,
                     @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
                     @JsonProperty("accountNonLocked") boolean accountNonLocked,
                     @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities) {
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
        return ObjectUtil.equal(username, "admin");
    }
}