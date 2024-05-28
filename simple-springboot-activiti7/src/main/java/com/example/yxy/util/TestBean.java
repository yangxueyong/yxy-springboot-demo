package com.example.yxy.util;

import com.example.yxy.entity.TestEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TestBean {
    @Lazy
    @Bean
    public TestEntity getTest(){
        return new TestEntity();
    }
}

