package com.example.yxy;

import cn.hippo4j.core.enable.EnableDynamicThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamicThreadPool
public class Hippo4jDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hippo4jDemoApplication.class, args);
    }

}
