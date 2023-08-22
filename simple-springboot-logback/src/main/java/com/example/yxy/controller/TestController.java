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
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    /**
     * 测试同步保存日志
     * @return {@link String}
     */
    @GetMapping("/saveLogSync")
    public String saveLogSync(){
        MDC.put("TRACE_ID", UUID.randomUUID().toString());
        //打印日志
        log.info(LogFilter.DB_MARKER_SYNC,"测试同步保存log");
        log.info("普通日志1");
        return "ok";
    }

    /**
     * 测试异步保存日志
     * @return {@link String}
     */
    @GetMapping("/saveLogAsync")
    public String saveLogAsync(){
        MDC.put("TRACE_ID", UUID.randomUUID().toString());
        //打印日志
        log.error(LogFilter.DB_MARKER_ASYNC,"测试异步保存log");
        log.info("普通日志2");
        return "ok";
    }

}
