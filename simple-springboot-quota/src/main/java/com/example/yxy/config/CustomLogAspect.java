package com.example.yxy.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;


/**
 * 自定义注解
 * @author yxy
 * @date 2023/09/06
 */
@Slf4j
@Aspect
@Component
public class CustomLogAspect {


    @Around("@within(customLogAnn) || @annotation(customLogAnn)")
    public Object aroundPut(ProceedingJoinPoint point, CustomLogAnn customLogAnn) throws Throwable {
        log.info("开始--->");
        Object proceed = point.proceed();
        log.info("结束--->");
        return proceed;
    }


}
