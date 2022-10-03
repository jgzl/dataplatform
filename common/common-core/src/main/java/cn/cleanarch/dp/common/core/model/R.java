package cn.cleanarch.dp.common.core.model;

import cn.cleanarch.dp.common.core.exception.ErrorCode;
import cn.cleanarch.dp.common.core.exception.enums.GlobalErrorCodeConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author li7hai26@gmail.com
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = GlobalErrorCodeConstants.SUCCESS.getCode();

    /**
     * 失败
     */
    public static final int ERROR = GlobalErrorCodeConstants.ERROR.getCode();

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> success() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> success(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> success(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> error() {
        return restResult(null, ERROR, null);
    }

    public static <T> R<T> error(String msg) {
        return restResult(null, ERROR, msg);
    }

    public static <T> R<T> error(T data) {
        return restResult(data, ERROR, null);
    }

    public static <T> R<T> error(T data, String msg) {
        return restResult(data, ERROR, msg);
    }

    public static <T> R<T> error(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static R<Void> error(ErrorCode errorCode) {
        R<Void> apiResult = new R<>();
        apiResult.setCode(errorCode.getCode());
        apiResult.setMsg(error().getMsg());
        return apiResult;
    }
}
