package com.example.yxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.yxy.mapper")
public class ShardingJdbcDemoApplication3 {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDemoApplication3.class, args);
    }

}
