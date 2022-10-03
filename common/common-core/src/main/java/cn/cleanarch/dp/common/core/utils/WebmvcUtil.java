package cn.cleanarch.dp.common.core.utils;

import cn.cleanarch.dp.common.core.model.R;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端工具类
 *
 * @author li7hai26@gmail.com
 */
@Slf4j
public class WebmvcUtil {

    private static final String UTF8 = "UTF-8";

    /**
     * 从请求头或者请求路径中获取参数(优先获取请求头中的参数,请求头权重更大)
     */
    public static String getParameterByHeaderOrPath(HttpServletRequest request, String name) {
        String value = getParameter(request, name);
        if (StrUtil.isBlank(value)) {
            value = getHeader(request, name);
        }
        return value;
    }

    /**
     * 获取String参数
     */
    public static String getParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
        return Convert.toStr(request.getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(HttpServletRequest request, String name) {
        return Convert.toInt(request.getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(HttpServletRequest request, String name, Integer defaultValue) {
        return Convert.toInt(request.getParameter(name), defaultValue);
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(HttpServletRequest request, String name) {
        return Convert.toBool(request.getParameter(name));
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(HttpServletRequest request, String name, Boolean defaultValue) {
        return Convert.toBool(request.getParameter(name), defaultValue);
    }

    public static String getHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }
        return urlDecode(value);
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        final Map<String, String> headerMap = new HashMap<>();

        final Enumeration<String> names = request.getHeaderNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            headerMap.put(name, request.getHeader(name));
        }

        return headerMap;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(response.getStatus());
            response.setContentType(ContentType.JSON.getValue());
            response.setCharacterEncoding(UTF8);
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
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
     * 返回http状态发生异常统一使用400
     * @param response
     * @param httpStatus
     * @param result
     */
    public static void out(HttpServletResponse response, HttpStatus httpStatus, R result) {
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.append(JacksonUtil.writeValueAsString(result));
        } catch (IOException e) {
            log.error("生成文件流失败:", e);
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    /**
     * 业务成功
     * @param response
     * @param result
     */
    public static void okOut(HttpServletResponse response, R result) {
        out(response,HttpStatus.OK,result);
    }

    /**
     * 业务失败
     * @param response
     * @param result
     */
    public static void errorOut(HttpServletResponse response, R result) {
        out(response,HttpStatus.BAD_REQUEST,result);
    }
}
