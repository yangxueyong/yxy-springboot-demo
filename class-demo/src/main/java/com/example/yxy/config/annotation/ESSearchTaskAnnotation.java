package com.example.yxy.config.annotation;


import java.lang.annotation.*;

/**
 * 定时任务日志注解
 *
 * @author nt_wuwenqiang
 * @date 2021/07/09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) 
@Documented
public @interface ESSearchTaskAnnotation {

    /**
     * 任务名称
     *
     * @return {@link String}
     */
    String searchId() default "";

    /**
     * 任务子名称
     *
     * @return {@link String}
     */
    String type() default "";

}