package com.khcm.user.web.admin.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author wangtao
 * @date 2017/2/27
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OperationLog {

    //操作名称
    String name() default "";

}
