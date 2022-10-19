package cn.cleanarch.dp.common.core.utils;

import cn.cleanarch.dp.common.core.model.R;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 客户端工具类
 *
 * @author li7hai26@gmail.com
 */
@Slf4j
@UtilityClass
public class WebmvcUtil extends org.springframework.web.util.WebUtils{

    private static final String UTF8 = "UTF-8";

    /**
     * 从请求头或者请求路径中获取参数(优先获取请求头中的参数,请求头权重更大)
     */
    public String getParameterByHeaderOrPath(HttpServletRequest request, String name) {
        String value = getParameter(request, name);
        if (StrUtil.isBlank(value)) {
            value = getHeader(request, name);
        }
        return value;
    }

    /**
     * 获取String参数
     */
    public String getParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    /**
     * 获取String参数
     */
    public String getParameter(HttpServletRequest request, String name, String defaultValue) {
        return Convert.toStr(request.getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public Integer getParameterToInt(HttpServletRequest request, String name) {
        return Convert.toInt(request.getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public Integer getParameterToInt(HttpServletRequest request, String name, Integer defaultValue) {
        return Convert.toInt(request.getParameter(name), defaultValue);
    }

    /**
     * 获取Boolean参数
     */
    public Boolean getParameterToBool(HttpServletRequest request, String name) {
        return Convert.toBool(request.getParameter(name));
    }

    /**
     * 获取Boolean参数
     */
    public Boolean getParameterToBool(HttpServletRequest request, String name, Boolean defaultValue) {
        return Convert.toBool(request.getParameter(name), defaultValue);
    }

    public String getHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }
        return urlDecode(value);
    }

    public Map<String, String> getHeaders(HttpServletRequest request) {
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
    public String renderString(HttpServletResponse response, String string) {
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
    public String urlDecode(String str) {
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
    public void out(HttpServletResponse response, HttpStatus httpStatus, R result) {
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
    public void okOut(HttpServletResponse response, R result) {
        out(response,HttpStatus.OK,result);
    }

    /**
     * 业务失败
     * @param response
     * @param result
     */
    public void errorOut(HttpServletResponse response, R result) {
        out(response,HttpStatus.BAD_REQUEST,result);
    }


    /**
     * 判断是否ajax请求 spring ajax 返回含有 ResponseBody 或者 RestController注解
     * @param handlerMethod HandlerMethod
     * @return 是否ajax请求
     */
    public boolean isBody(HandlerMethod handlerMethod) {
        ResponseBody responseBody = ClassUtils.getAnnotation(handlerMethod, ResponseBody.class);
        return responseBody != null;
    }

    /**
     * 读取cookie
     * @param name cookie name
     * @return cookie value
     */
    public String getCookieVal(String name) {
        if (getRequest().isPresent()) {
            return getCookieVal(getRequest().get(), name);
        }
        return null;
    }

    /**
     * 读取cookie
     * @param request HttpServletRequest
     * @param name cookie name
     * @return cookie value
     */
    public String getCookieVal(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 清除 某个指定的cookie
     * @param response HttpServletResponse
     * @param key cookie key
     */
    public void removeCookie(HttpServletResponse response, String key) {
        setCookie(response, key, null, 0);
    }

    /**
     * 设置cookie
     * @param response HttpServletResponse
     * @param name cookie name
     * @param value cookie value
     * @param maxAgeInSeconds maxage
     */
    public void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 获取 HttpServletRequest
     * @return {HttpServletRequest}
     */
    public Optional<HttpServletRequest> getRequest() {
        return Optional
                .ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }

    /**
     * 获取 HttpServletResponse
     * @return {HttpServletResponse}
     */
    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 返回附件
     *
     * @param response 响应
     * @param filename 文件名
     * @param content 附件内容
     * @throws IOException
     */
    public static void writeAttachment(HttpServletResponse response, String filename, byte[] content) throws IOException {
        // 设置 header 和 contentType
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // 输出附件
        IoUtil.write(response.getOutputStream(), false, content);
    }
}
