package cn.cleanarch.dp.common.core.constant;

/**
 * 缓存的key 常量
 *
 * @author li7hai26@gmail.com
 */
public class CacheConstants extends CommonConstants{

    /**
     * 缓存有效期，默认720（分钟）
     */
    public final static long EXPIRATION = 720;

    /**
     * 缓存刷新时间，默认120（分钟）
     */
    public final static long REFRESH_TIME = 120;

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = CACHE_PREFIX + "login_tokens:";

    /**
     * 验证码缓存key
     */
    public final static String DEFAULT_CODE_KEY = CACHE_PREFIX + "validate_code:";

    /**
     * 网关访问存放
     */
    public final static String ACCESS_CONF_KEY = CACHE_PREFIX + "gateway_access_conf_key";

    /**
     * 网关访问内存reload 时间
     */
    public final static String ACCESS_CONF_JVM_RELOAD_TOPIC = CACHE_PREFIX + "access_conf_jvm_route_reload_topic";

    /**
     * 路由存放
     */
    public final static String ROUTE_KEY = CACHE_PREFIX + "gateway_route_key";

    /**
     * 内存reload 时间
     */
    public final static String ROUTE_JVM_RELOAD_TOPIC = CACHE_PREFIX + "gateway_jvm_route_reload_topic";

    /**
     * redis 重新加载 路由信息
     */
    public final static String ROUTE_REDIS_RELOAD_TOPIC = CACHE_PREFIX + "redis_route_reload_topic";

    /**
     * 菜单信息缓存
     */
    public final static String MENU_DETAILS = CACHE_PREFIX + "menu_details";

    /**
     * 用户信息缓存
     */
    public final static String USER_DETAILS = CACHE_PREFIX + "user_details";

    /**
     * 角色信息缓存
     */
    public final static String ROLE_DETAILS = CACHE_PREFIX + "role_details";
}
