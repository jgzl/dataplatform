package cn.cleanarch.dp.common.websocket.config;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * webSocket配置类
 *
 * @author shuangfei.zhu@hand-china.com 2019/04/19 11:43
 */
@Component
@ConfigurationProperties(prefix = WebSocketProperties.PREFIX)
public class WebSocketProperties {

    static final String PREFIX = CommonConstants.CONFIGURATION_PREFIX+".websocket";
    /**
     * websocket路径
     */
    private String websocket = "/websocket";
    /**
     * sockJs路径
     */
    private String sockJs = "/sock-js";
    /**
     * redisDb
     */
    private Integer redisDb = 1;
    /**
     * 心跳内容
     */
    private String heartbeat = "websocket-heartbeat";
    /**
     * 后端长连通信密钥
     */
    private String secretKey = "websocket-secret";

    /**
     * 线程池配置
     */
    private ThreadPoolProperties threadPoolProperties = new ThreadPoolProperties();

    /**
     * Thread Pool Properties
     *
     * @author gaokuo.dai@hand-china.com 2018年8月21日下午4:00:19
     */
    public static class ThreadPoolProperties {
        /**
         * 核心线程数 默认 2
         */
        private int corePoolSize = 2;
        /**
         * 最大线程数 默认 10
         */
        private int maxPoolSize = 5;
        /**
         * 线程完成任务后的待机存活时间 默认 60
         */
        private int keepAliveSeconds = 15;
        /**
         * 等待队列长度 默认 Integer.MAX_VALUE
         */
        private int queueCapacity = 0;
        /**
         * 是否允许停止闲置核心线程 默认 false
         */
        private boolean allowCoreThreadTimeOut = false;
        /**
         * 线程名前缀 默认 hzero.import
         */
        private String threadNamePrefix = "websocket-check";

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public int getKeepAliveSeconds() {
            return keepAliveSeconds;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public boolean isAllowCoreThreadTimeOut() {
            return allowCoreThreadTimeOut;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public void setKeepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
            this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }

    }

    public String getWebsocket() {
        return websocket;
    }

    public WebSocketProperties setWebsocket(String websocket) {
        this.websocket = websocket;
        return this;
    }

    public String getSockJs() {
        return sockJs;
    }

    public WebSocketProperties setSockJs(String sockJs) {
        this.sockJs = sockJs;
        return this;
    }

    public Integer getRedisDb() {
        return redisDb;
    }

    public WebSocketProperties setRedisDb(Integer redisDb) {
        this.redisDb = redisDb;
        return this;
    }

    public String getHeartbeat() {
        return heartbeat;
    }

    public WebSocketProperties setHeartbeat(String heartbeat) {
        this.heartbeat = heartbeat;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public WebSocketProperties setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public ThreadPoolProperties getThreadPoolProperties() {
        return threadPoolProperties;
    }

    public WebSocketProperties setThreadPoolProperties(ThreadPoolProperties threadPoolProperties) {
        this.threadPoolProperties = threadPoolProperties;
        return this;
    }
}
