package com.github.jgzl.gw.common.core.annotaion;

import java.lang.annotation.*;

/**
 * 定义接口版本号
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiVersion {

    String value() default "v1";

}
