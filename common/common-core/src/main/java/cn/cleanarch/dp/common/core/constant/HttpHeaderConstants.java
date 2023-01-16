package cn.cleanarch.dp.common.core.constant;

public class HttpHeaderConstants {
    /**
     * 访问环境
     */
    public static final String X_DATAPLATFORM_API_ENV = "x-dataplatform-api-env";
    /**
     * 访问key
     */
    public static final String X_DATAPLATFORM_API_KEY = "x-dataplatform-api-key";
    /**
     * 访问secret
     */
    public static final String X_DATAPLATFORM_API_SECRET = "x-dataplatform-api-secret";
    /**
     * 访问系统
     */
    public static final String X_DATAPLATFORM_API_SYSTEM = "x-dataplatform-api-system";
    /**
     * 访问API类型
     * 1.请求头不存在此参数时,请求头必须有x-dataplatform-api-key/secret/system/env
     * 2.等于token时,使用x-dataplatform-api-key/secret获取到token,再次发起请求时,请求头必须有x-dataplatform-api-token
     * 3.等于none时,绕过网关鉴权请求
     */
    public static final String X_DATAPLATFORM_API_TYPE = "x-dataplatform-api-type";
    /**
     * 访问版本
     */
    public static final String X_DATAPLATFORM_API_VERSION = "x-dataplatform-api-version";
    /**
     * 流量染色-是否灰度
     */
    public static final String X_DATAPLATFORM_METADATA_TRANSITIVE_GRAY = "x-dataplatform-metadata-transitive-gray";
    /**
     * 流量染色-是否灰度
     */
    public static final String X_DATAPLATFORM_METADATA_TRANSITIVE_GRAY_TAG = "x-dataplatform-metadata-transitive-gray-tag";
}
