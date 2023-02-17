package cn.cleanarch.dp.common.job.annotation;

import cn.cleanarch.dp.common.job.model.ExecutorRouteStrategyEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XxlRegister {

    String cron();

    String jobDesc() default "default jobDesc";

    String author() default "default Author";

    ExecutorRouteStrategyEnum route() default ExecutorRouteStrategyEnum.FIRST;

    int triggerStatus() default 0;
}
