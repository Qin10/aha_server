package cn.hdustea.aha_server.userOperationLog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明记录日志的方法的注解
 *
 * @author STEA_YY
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogUserOperation {
    String value() default "";
}