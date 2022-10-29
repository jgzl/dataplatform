package cn.cleanarch.dp.common.core.constant;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/16
 */
public class CommonConstants {

    /**
     * 删除
     */
    public final static String STATUS_DEL = "1";

    /**
     * 正常
     */
    public final static String STATUS_NORMAL = "0";

    /**
     * 默认版本
     */
    public final static Integer DEFAULT_VERSION = 1;

    /**
     * 锁定
     */
    public static final String STATUS_LOCK = "9";

    /**
     * 菜单树根节点
     */
    public static final String TREE_ROOT_ID = "-1";

    /**
     * 系统标志前缀
     */
    public final static String CONFIGURATION_PREFIX = "infra";

    /**
     * 系统标志前缀
     */
    public final static String CACHE_PREFIX = CONFIGURATION_PREFIX +":";

    /**
     * 网站图标路径
     */
    public final static String FAVICON_ICO_URL = "/favicon.ico";

    /**
     * 心跳接口路径
     */
    public final static String HEART_BEAT_URL = "/heartbeat";

    /**
     * SpringBoot Admin Client actuator接口路径
     */
    public final static String ACTUATOR_URL = "/actuator";
    public static final String CURRENT = "current" ;
    public static final String SIZE = "size" ;
}
