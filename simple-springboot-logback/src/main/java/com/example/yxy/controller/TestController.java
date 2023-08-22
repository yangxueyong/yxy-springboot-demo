package com.example.yxy.controller;

import com.example.yxy.config.log.LogFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/saveLog")
    public String getTest(){
        MDC.put("TRACE_ID","123");
        //打印日志
        log.info(LogFilter.DB_Marker,"测试保存log");
        return "ok";
    }

}
