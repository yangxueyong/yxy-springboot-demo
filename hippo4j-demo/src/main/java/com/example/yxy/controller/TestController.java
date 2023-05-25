package com.example.yxy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ThreadPoolExecutor messageConsumeDynamicExecutor;

    @Resource
    private ThreadPoolExecutor messageProduceDynamicExecutor;

    @GetMapping("/runTaskConsume/{time}/{content}")
    public String runTaskConsume(@PathVariable("time") long time,
                                 @PathVariable("content") String content){
        messageConsumeDynamicExecutor.execute(()->{
            System.out.println("content1===>"+content);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return "ok";
    }

    @GetMapping("/runTaskProduce/{time}/{content}")
    public String runTaskProduce(@PathVariable("time") long time,
                                 @PathVariable("content") String content){
        messageProduceDynamicExecutor.execute(()->{
            System.out.println("content2===>"+content);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return "ok";
    }
}
