package com.example.yxy;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//使用这个注解可以使用AopContext.currentProxy来获取service对象
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@MapperScan("com.example.yxy.mapper")
@EnableEncryptableProperties
@EnableWebSecurity
public class ActivitiDemoApplication4 {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiDemoApplication4.class, args);
    }

}
