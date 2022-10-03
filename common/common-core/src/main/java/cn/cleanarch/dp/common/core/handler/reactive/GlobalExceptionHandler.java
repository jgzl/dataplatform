package cn.cleanarch.dp.common.core.handler.reactive;

import cn.cleanarch.dp.common.core.exception.DataException;
import cn.cleanarch.dp.common.core.exception.ServiceException;
import cn.cleanarch.dp.common.core.exception.enums.GlobalErrorCodeConstants;
import cn.cleanarch.dp.common.core.model.R;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * 全局异常处理器，将 Exception 翻译成 R + 对应的异常编号
 *
 * @author lihaifeng
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
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
     * 因为 Filter 不走 SpringWebflux 的流程，但是我们又需要兜底处理异常，所以这里提供一个全量的异常处理过程，保持逻辑统一。
     *
     * @param request 请求
     * @param ex 异常
     * @return 通用返回
     */
    public R<Void> allExceptionHandler(ServerWebExchange request, Throwable ex) {
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
        if (ex instanceof RequestNotPermitted) {
            return requestNotPermittedExceptionHandler(request, (RequestNotPermitted) ex);
        }
        if (ex instanceof ResponseStatusException) {
            return responseStatusExceptionHandler(request, (ResponseStatusException) ex);
        }
        if (ex instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) ex);
        }
        return defaultExceptionHandler(request, ex);
    }

    /**
     * 处理请求参数类型错误
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return R.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", ex.getMessage()));
    }

    /**
     * 处理 Spring Webflux参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        log.warn("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警
        return R.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 Spring Webflux参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> bindExceptionHandler(BindException ex) {
        log.warn("[handleBindException]", ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null; // 断言，避免告警
        return R.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return R.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }

    /**
     * 处理 Dubbo Consumer 本地参数校验时，抛出的 ValidationException 异常
     */
    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> validationException(ValidationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        // 无法拼接明细的错误信息，因为 Dubbo Consumer 抛出 ValidationException 异常时，是直接的字符串信息，且人类不可读
        return R.error(GlobalErrorCodeConstants.BAD_REQUEST);
    }

    /**
     * 处理 Resilience4j 限流抛出的异常
     */
    @ExceptionHandler(value = RequestNotPermitted.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public R<Void> requestNotPermittedExceptionHandler(ServerWebExchange req, RequestNotPermitted ex) {
        log.warn("[requestNotPermittedExceptionHandler][url({}) 访问过于频繁]", req.getRequest().getURI(), ex);
        return R.error(GlobalErrorCodeConstants.TOO_MANY_REQUESTS);
    }

    /**
     * 返回状态异常
     */
    @ExceptionHandler(value = ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> responseStatusExceptionHandler(ServerWebExchange req, ResponseStatusException ex) {
        log.warn("[responseStatusExceptionHandler]", ex);
        if (HttpStatus.NOT_FOUND.equals(ex.getStatus())) {
            return R.error(ex.getStatus().value(), String.format("无法找到:[%s]",req.getRequest().getURI()));
        } else {
            return R.error(ex.getStatus().value(), ex.getMessage());
        }
    }

    /**
     * 处理业务异常 ServiceException
     *
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(value = ServiceException.class)
    public R<Void> serviceExceptionHandler(ServiceException ex) {
        log.info("[serviceExceptionHandler]", ex);
        return R.error(ex.getCode().intValue(), ex.getMessage());
    }

    /**
     * 处理业务异常 DataException
     *
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(value = DataException.class)
    public R<Void> serviceExceptionHandler(DataException ex) {
        log.info("[serviceExceptionHandler]", ex);
        return R.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> defaultExceptionHandler(ServerWebExchange req, Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        return R.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getMsg());
    }
}
