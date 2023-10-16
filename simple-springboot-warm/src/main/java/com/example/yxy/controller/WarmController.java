package com.example.yxy.controller;

import com.alibaba.fastjson2.JSON;
import com.example.yxy.entity.TestAutoIdEntity;
import com.example.yxy.entity.TestEntity;
import com.example.yxy.service.TestService;
import com.example.yxy.util.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/warm")
public class WarmController {

    @Autowired
    private TestBean testBean;

    @Autowired
    private TestEntity testEntity;

    @Autowired
    private TestService testService;

    @RequestMapping("/warm-up")
    public TestEntity getTest(){
        TestEntity testEntity = new TestEntity();
        testEntity.setId("xx1");
        testEntity.setQutoTime(new Date());

        testService.testAnn2();
        return testEntity;
    }


}
