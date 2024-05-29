package com.luban.server.lbtransaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lbtransactional {
    // 代表属于分布式事务


    boolean isStart() default false;
    boolean isEnd() default false;
}
