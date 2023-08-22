package com.example.yxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.yxy.mapper")
public class TestDemoApplication4 {

    public static void main(String[] args) {
        SpringApplication.run(TestDemoApplication4.class, args);
    }

}
