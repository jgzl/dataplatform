package cn.cleanarch.dp.common.websocket.constant;

import cn.cleanarch.dp.common.core.constant.CommonConstants;

/**
 * webSocket常量类
 *
 * @author li7hai26@outlook.com 2019/04/19 11:07
 */
public class WebSocketConstant {

    private WebSocketConstant() {
    }

    public static final String REDIS_KEY = CommonConstants.CACHE_PREFIX+"websocket:";

    public static final String ACCESS_TOKEN = REDIS_KEY+"access_token";

    public static final String CHANNEL = REDIS_KEY+"channel";

    public static final String ALIVE = "alive";

    public static final String DEFAULT_PROCESSOR = "websocket-default-processor";

    /**
     * 连接参数
     */
    public static final class Attributes {
        private Attributes() {
        }

        /**
         * token
         */
        public static final String TOKEN = "access_token";
        public static final String SECRET_KEY = "secret_key";
        public static final String GROUP = "group";
        public static final String PROCESSOR = "processor";
    }

    public static final class SendType {
        private SendType() {
        }

        // 用户消息
        public static final String SESSION = "S";
        public static final String USER = "U";
        public static final String ALL = "A";
        // 服务消息
        public static final String S_SESSION = "SS";
        public static final String S_GROUP = "SG";
        public static final String S_ALL = "SA";
        // 关闭连接
        public static final String CLOSE = "CLOSE";
    }
}
