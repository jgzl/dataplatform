package cn.cleanarch.dp.common.core.constant;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/20
 */
public class GatewayConstants extends HttpHeaderConstants{

    /**
     * 网关配置统一前缀
     */
    public static final String CONFIGURATION_PREFIX = CommonConstants.CONFIGURATION_PREFIX + ".gateway";

    /**
     * 网关动态路由访问统一前缀
     */
    public static final String API_PREFIX = "/api/";

    /**
     * 网关管理访问统一前缀
     */
    public static final String ADMIN_PREFIX = "/admin";
}
