package cn.cleanarch.dp.common.core.utils;

import cn.cleanarch.dp.common.core.exception.ErrorCode;
import cn.cleanarch.dp.common.core.model.R;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 客户端工具类
 *
 * @author li7hai26@gmail.com
 */
@Slf4j
public class WebfluxUtil {

    private static final String UTF8 = "UTF-8";

    /**
     * 从请求头或者请求路径中获取参数(优先获取请求头中的参数,请求头权重更大)
     */
    public static String getParameterByHeaderOrPath(ServerHttpRequest request, String name) {
        String value = getHeader(request, name);
        if (StrUtil.isBlank(value)) {
            value = getParameter(request, name);
        }
        return value;
    }

    /**
     * 获取String参数
     */
    public static String getParameter(ServerHttpRequest request, String name) {
        return request.getQueryParams().getFirst(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(ServerHttpRequest request, String name, String defaultValue) {
        return Convert.toStr(request.getQueryParams().getFirst(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(ServerHttpRequest request, String name) {
        return Convert.toInt(request.getQueryParams().getFirst(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(ServerHttpRequest request, String name, Integer defaultValue) {
        return Convert.toInt(request.getQueryParams().getFirst(name), defaultValue);
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(ServerHttpRequest request, String name) {
        return Convert.toBool(request.getQueryParams().getFirst(name));
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(ServerHttpRequest request, String name, Boolean defaultValue) {
        return Convert.toBool(request.getQueryParams().getFirst(name), defaultValue);
    }

    public static String getHeader(ServerHttpRequest request, String name) {
        String value = request.getHeaders().getFirst(name);
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }
        return urlDecode(value);
    }

    public static Map<String, String> getHeaders(ServerHttpRequest request) {
        return request.getHeaders().toSingleValueMap();
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        response.setStatus(org.springframework.http.HttpStatus.OK.value());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            response.getWriter().print(string);
        } catch (IOException e) {
            log.error("生成文件流失败:", e);
        }
        return null;
    }

    /**
     * 内容解码
     *
     * @param str 内容
     * @return 解码后的内容
     */
    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param value    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> out(ServerHttpResponse response, HttpStatus httpStatus, String value) {
        return out(response, httpStatus, R.ERROR, value);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param result 通用返回状态码
     * @return Mono<Void>
     */
    public static Mono<Void> out(ServerHttpResponse response, HttpStatus httpStatus, R<?> result) {
        return out(response, httpStatus, result.getCode(), result.getMsg());
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param errorCode 错误状态吗枚举
     * @return Mono<Void>
     */
    public static Mono<Void> out(ServerHttpResponse response, HttpStatus httpStatus, ErrorCode errorCode) {
        return out(response, httpStatus, errorCode.getCode(), errorCode.getMsg());
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param status   http状态码
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> out(ServerHttpResponse response, HttpStatus status, int code, String value) {
        return out(response, MediaType.APPLICATION_JSON_VALUE, status, code, value);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response    ServerHttpResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param code        响应状态码
     * @param value       响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> out(ServerHttpResponse response, String contentType, HttpStatus status, int code, String value) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        R<Void> result = R.error(code, value);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JacksonUtil.toJsonByte(result));
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param result 错误状态吗枚举
     * @return Mono<Void>
     */
    public static Mono<Void> errorOut(ServerHttpResponse response, R<?> result) {
        return out(response,HttpStatus.BAD_REQUEST,result);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param errorCode 错误状态吗枚举
     * @return Mono<Void>
     */
    public static Mono<Void> errorOut(ServerHttpResponse response, ErrorCode errorCode) {
        return out(response,HttpStatus.BAD_REQUEST,errorCode);
    }


    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param result 错误状态吗枚举
     * @return Mono<Void>
     */
    public static Mono<Void> okOut(ServerHttpResponse response, R<?> result) {
        return out(response,HttpStatus.OK,result);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param errorCode 错误状态吗枚举
     * @return Mono<Void>
     */
    public static Mono<Void> okOut(ServerHttpResponse response, ErrorCode errorCode) {
        return out(response,HttpStatus.OK,errorCode);
    }
}
