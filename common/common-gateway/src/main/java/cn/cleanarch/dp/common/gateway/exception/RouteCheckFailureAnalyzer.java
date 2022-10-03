package cn.cleanarch.dp.common.gateway.exception;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * @author li7hai26@gmail.com
 * @date 2021/11/19
 * <p>
 * 格式化异常信息，方便启动时观察。
 */
public class RouteCheckFailureAnalyzer extends AbstractFailureAnalyzer<RouteCheckException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, RouteCheckException cause) {
        return new FailureAnalysis(cause.getMessage(), "必须先启动网关管理服务初始化路由,再启动网关核心服务", cause);
    }

}
