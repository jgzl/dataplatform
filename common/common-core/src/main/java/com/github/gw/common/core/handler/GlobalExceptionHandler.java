package com.github.gw.common.core.handler;

import com.github.gw.common.core.domain.R;
import com.github.gw.common.core.exception.ServiceException;
import com.github.gw.common.core.utils.JacksonUtils;
import com.github.gw.common.model.system.dto.ApiErrorLogCreateReqDTO;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import static com.github.gw.common.core.exception.enums.GlobalErrorCodeConstants.*;

/**
 * 全局异常处理器，将 Exception 翻译成 R + 对应的异常编号
 *
 * @author lihaifeng
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Component
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 应用名
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 处理所有异常，主要是提供给 Filter 使用
     * 因为 Filter 不走 SpringMVC 的流程，但是我们又需要兜底处理异常，所以这里提供一个全量的异常处理过程，保持逻辑统一。
     *
     * @param request 请求
     * @param ex 异常
     * @return 通用返回
     */
    public R<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return methodArgumentTypeMismatchExceptionHandler((MethodArgumentTypeMismatchException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionExceptionHandler((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return bindExceptionHandler((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) ex);
        }
        if (ex instanceof ValidationException) {
            return validationException((ValidationException) ex);
        }
        if (ex instanceof NoHandlerFoundException) {
            return noHandlerFoundExceptionHandler((NoHandlerFoundException) ex);
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return httpRequestMethodNotSupportedExceptionHandler((HttpRequestMethodNotSupportedException) ex);
        }
        if (ex instanceof RequestNotPermitted) {
            return requestNotPermittedExceptionHandler(request, (RequestNotPermitted) ex);
        }
        if (ex instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionHandler(request, (AccessDeniedException) ex);
        }
        return defaultExceptionHandler(request, ex);
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return R.fail(BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return R.fail(BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", ex.getMessage()));
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        log.warn("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警
        return R.fail(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    public R<?> bindExceptionHandler(BindException ex) {
        log.warn("[handleBindException]", ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null; // 断言，避免告警
        return R.fail(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return R.fail(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }

    /**
     * 处理 Dubbo Consumer 本地参数校验时，抛出的 ValidationException 异常
     */
    @ExceptionHandler(value = ValidationException.class)
    public R<?> validationException(ValidationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        // 无法拼接明细的错误信息，因为 Dubbo Consumer 抛出 ValidationException 异常时，是直接的字符串信息，且人类不可读
        return R.fail(BAD_REQUEST);
    }

    /**
     * 处理 SpringMVC 请求地址不存在
     *
     * 注意，它需要设置如下两个配置项：
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public R<?> noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
        log.warn("[noHandlerFoundExceptionHandler]", ex);
        return R.fail(NOT_FOUND.getCode(), String.format("请求地址不存在:%s", ex.getRequestURL()));
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     *
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return R.fail(METHOD_NOT_ALLOWED.getCode(), String.format("请求方法不正确:%s", ex.getMessage()));
    }

    /**
     * 处理 Resilience4j 限流抛出的异常
     */
    @ExceptionHandler(value = RequestNotPermitted.class)
    public R<?> requestNotPermittedExceptionHandler(HttpServletRequest req, RequestNotPermitted ex) {
        log.warn("[requestNotPermittedExceptionHandler][url({}) 访问过于频繁]", req.getRequestURL(), ex);
        return R.fail(TOO_MANY_REQUESTS);
    }

    /**
     * 处理 Spring Security 权限不足的异常
     *
     * 来源是，使用 @PreAuthorize 注解，AOP 进行权限拦截
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public R<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
//        Long userId = WebFrameworkUtils.getLoginUserId();
        Long userId = -1L;
        log.warn("[accessDeniedExceptionHandler][userId({}) 无法访问 url({})]",  userId,
                req.getRequestURL(), ex);
        return R.fail(FORBIDDEN);
    }

    /**
     * 处理业务异常 ServiceException
     *
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(value = ServiceException.class)
    public R<?> serviceExceptionHandler(ServiceException ex) {
        log.info("[serviceExceptionHandler]", ex);
        return R.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public R<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        // 插入异常日志
        // this.createExceptionLog(req, ex);
        // 返回 ERROR R
        return R.fail(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMsg());
    }

    private void createExceptionLog(HttpServletRequest req, Throwable e) {
        // 插入错误日志
        ApiErrorLogCreateReqDTO errorLog = new ApiErrorLogCreateReqDTO();
        try {
            // 初始化 errorLog
//            initExceptionLog(errorLog, req, e);
            // 执行插入 errorLog
//            apiErrorLogFrameworkService.createApiErrorLogAsync(errorLog);
        } catch (Throwable th) {
            log.error("[createExceptionLog][url({}) log({}) 发生异常]", req.getRequestURI(),  JacksonUtils.toJsonString(errorLog), th);
        }
    }

//    private void initExceptionLog(ApiErrorLogCreateReqDTO errorLog, HttpServletRequest request, Throwable e) {
//        // 处理用户信息
//        errorLog.setUserId(WebFrameworkUtils.getLoginUserId(request));
//        errorLog.setUserType(WebFrameworkUtils.getLoginUserType(request));
//        // 设置异常字段
//        errorLog.setExceptionName(e.getClass().getName());
//        errorLog.setExceptionMessage(ExceptionUtil.getMessage(e));
//        errorLog.setExceptionRootCauseMessage(ExceptionUtil.getRootCauseMessage(e));
//        errorLog.setExceptionStackTrace(ExceptionUtils.getStackTrace(e));
//        StackTraceElement[] stackTraceElements = e.getStackTrace();
//        Assert.notEmpty(stackTraceElements, "异常 stackTraceElements 不能为空");
//        StackTraceElement stackTraceElement = stackTraceElements[0];
//        errorLog.setExceptionClassName(stackTraceElement.getClassName());
//        errorLog.setExceptionFileName(stackTraceElement.getFileName());
//        errorLog.setExceptionMethodName(stackTraceElement.getMethodName());
//        errorLog.setExceptionLineNumber(stackTraceElement.getLineNumber());
//        // 设置其它字段
//        errorLog.setTraceId(TracerUtils.getTraceId());
//        errorLog.setApplicationName(applicationName);
//        errorLog.setRequestUrl(request.getRequestURI());
//        Map<String, Object> requestParams = MapUtil.<String, Object>builder()
//                .put("query", ServletUtil.getParamMap(request))
//                .put("body", ServletUtil.getBody(request)).build();
//        errorLog.setRequestParams(JsonUtils.toJsonString(requestParams));
//        errorLog.setRequestMethod(request.getMethod());
//        errorLog.setUserAgent(ServletUtils.getUserAgent(request));
//        errorLog.setUserIp(ServletUtil.getClientIP(request));
//        errorLog.setExceptionTime(new Date());
//    }

}