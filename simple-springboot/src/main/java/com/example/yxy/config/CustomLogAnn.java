package com.example.yxy.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;


/**
 * 自定义缓存
 *
 * @author yangxueyong
 * @date 2021/06/01
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomLogAnn {

}
