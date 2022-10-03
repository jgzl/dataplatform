package cn.cleanarch.dp.common.core.spi;

import java.lang.annotation.*;

/**
 * Join
 * Adding this annotation to a class indicates joining the extension mechanism.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Join {
}
