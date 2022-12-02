package com.example.yxy.controller;

import com.example.yxy.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/queryByCsv")
    private String queryByCsv() throws Exception {
        testService.queryByCsv();
        return "ok";
    }
    @RequestMapping("/queryByWhite")
    private String queryByWhite() throws Exception {
        testService.queryByWhite();
        return "ok";
    }
}
