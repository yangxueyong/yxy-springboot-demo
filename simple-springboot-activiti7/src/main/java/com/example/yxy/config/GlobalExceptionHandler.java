package com.example.yxy.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    // 通过注解指定了响应的状态码,前台$.ajax会在error函数的xhr响应中接收错误json
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<Map<String, String>> BindExceptionHandle(BindException errors) {
		
		// 存放所有error信息的List
        List<Map<String, String>> errorList = new ArrayList<>();
		
        for(FieldError err : errors.getFieldErrors()){
			
			// 根据当前的FieldError对象从国际化资源文件中获取信息
            String msg = this.messageSource.getMessage(err, LocaleContextHolder.getLocale());
			
			// 封装错误信息
            Map<String, String> errorMap = new HashMap<String, String>() {
                {
                    put("field", err.getField());
                    put("msg", msg);

                }
            };
            errorList.add(errorMap);
        }

        return errorList;
    }
}