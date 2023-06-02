package com.example.yxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.yxy.mapper")
public class ShardingJdbcDemoApplication4 {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDemoApplication4.class, args);
    }

}
