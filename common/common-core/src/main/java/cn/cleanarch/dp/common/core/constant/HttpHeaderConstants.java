package cn.cleanarch.dp.common.core.constant;

public class HttpHeaderConstants {
    /**
     * 访问环境
     */
    public static final String X_BUSINESS_API_ENV = "x-business-api-env";
    /**
     * 访问key
     */
    public static final String X_BUSINESS_API_KEY = "x-business-api-key";
    /**
     * 访问secret
     */
    public static final String X_BUSINESS_API_SECRET = "x-business-api-secret";
    /**
     * 访问系统
     */
    public static final String X_BUSINESS_API_SYSTEM = "x-business-api-system";
    /**
     * 访问API类型
     * 1.请求头不存在此参数时,请求头必须有x-business-api-key/secret/system/env
     * 2.等于token时,使用x-business-api-key/secret获取到token,再次发起请求时,请求头必须有x-business-api-token
     * 3.等于none时,绕过网关鉴权请求
     */
    public static final String X_BUSINESS_API_TYPE = "x-business-api-type";
    /**
     * 访问版本
     */
    public static final String X_BUSINESS_API_VERSION = "x-business-api-version";
    /**
     * 流量染色-是否灰度
     */
    public static final String X_BUSINESS_METADATA_TRANSITIVE_GRAY = "x-business-metadata-transitive-gray";
    /**
     * 流量染色-是否灰度
     */
    public static final String X_BUSINESS_METADATA_TRANSITIVE_GRAY_TAG = "x-business-metadata-transitive-gray-tag";
}
