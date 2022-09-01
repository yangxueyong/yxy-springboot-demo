package com.example.yxy;

import com.example.yxy.config.annotation.ESSearchTaskAnnotation;
import com.example.yxy.config.exec.MethodExecUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ClassDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassDemoApplication.class, args);
    }
}
