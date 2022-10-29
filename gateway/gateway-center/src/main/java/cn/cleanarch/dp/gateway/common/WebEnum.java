package cn.cleanarch.dp.gateway.common;

/**
 * Web枚举
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
public enum WebEnum {

    REQUEST("REQUEST", "请求"),
    RESPONSE("RESPONSE", "响应"),
    ;

    String code;
    String msg;

    WebEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
