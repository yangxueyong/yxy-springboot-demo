package com.example.yxy.controller;

import com.example.yxy.entity.TestEntity;
import com.example.yxy.service.TestService;
import com.example.yxy.service.TestServiceImpl;
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
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestBean testBean;

    @Autowired
    private TestEntity testEntity;

    @Autowired
    private TestService testService;

    @GetMapping("/getTest")
    public TestEntity getTest(){
        TestEntity testEntity = new TestEntity();
        testEntity.setId("xx1");
        testEntity.setQutoTime(new Date());
        return testEntity;
    }

    @GetMapping("/getTestBean")
    public TestEntity getTestBean(){
        TestEntity testEntity1 = testBean.getTest();
        System.out.println("testEntity0->"+testEntity);
        System.out.println("testEntity1->"+testEntity1);

        return testEntity1;
    }

    @GetMapping("/selectTimeOut")
    public List selectTimeOut(){
        List list = testService.selectTimeOut();
        System.out.println("list->"+list);

        return list;
    }

    @GetMapping("/testAnn1")
    public String testAnn1(){
        testService.testAnn1();
        return "ok";
    }

    /**
     * 查询数据
     */
//    @PostConstruct
    void selectTimeOutTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                try {
                    List list = testService.selectTimeOut();
                    System.out.println(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }
}
