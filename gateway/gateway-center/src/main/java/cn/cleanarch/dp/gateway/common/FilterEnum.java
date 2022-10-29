package cn.cleanarch.dp.gateway.common;

/**
 * 过滤器枚举
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
public enum FilterEnum {

    EXCLUDE_PATH(-30, "黑/白名单,不记录日志过滤器"),
    LOG_AOP(-20, "请求响应日志记录过滤器"),
    FILE_SIZE(-10, "文件大小限制过滤器"),
    ;

    private final Integer code;
    private final String name;

    FilterEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
