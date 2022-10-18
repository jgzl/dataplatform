package cn.cleanarch.dp.common.core.constant;

/**
 * 权限相关通用常量
 *
 * @author li7hai26@gmail.com
 */
public class SecurityConstants {
    /**
     * 登录接口
     */
    public static final String LOGIN_PATH = "/user/login";
    /**
     * 登出接口
     */
    public static final String LOGOUT_PATH = "/user/logout";

    /**
     * 内部
     */
    public static final String FROM_IN = "Y";

    /**
     * 标志
     */
    public static final String FROM = "from";

    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "userId";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 用户信息字段
     */
    public static final String DETAILS_USER_INFO = "userInfo";

    /**
     * 角色字段
     */
    public static final String DETAILS_ROLE = "role";

    /**
     * 权限字段
     */
    public static final String DETAILS_PERMISSIONS = "permissions";

    /**
     * 请求来源
     */
    public static final String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    public static final String INNER = "inner";

    /**
     * 用户标识
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "login_user";

    /**
     * 角色前缀
     */
    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * 角色前缀
     */
    public static final String ROLE_ADMIN = "ROLE_admin";

    /**
     * {bcrypt} 加密的特征码
     */
    public static final String BCRYPT = "{bcrypt}";

    /**
     * {noop} 加密的特征码
     */
    public static final String NOOP = "{noop}";

    /**
     * 默认用户-超级管理员
     */
    public static final String DEFAULT_USER_ID = "1";

    /**
     * 默认用户-超级管理员
     */
    public static final String DEFAULT_USER = "admin";
    /**
     * app自定义授权
     */
    public static final String APP = "app";
    public static final String CLIENT_ID = "clientId";
    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String PROJECT_LICENSE = "https://www.baidu.com";
    public static final String CUSTOM_CONSENT_PAGE_URI = "/token/confirm_access";
    public static final String SMS_PARAMETER_NAME = "mobile";
}
