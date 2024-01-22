package com.example.yxy;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//使用这个注解可以使用AopContext.currentProxy来获取service对象
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@MapperScan("com.example.yxy.mapper")
@EnableEncryptableProperties
public class TestDemoApplication4 {

    public static void main(String[] args) {
        SpringApplication.run(TestDemoApplication4.class, args);
    }

}
