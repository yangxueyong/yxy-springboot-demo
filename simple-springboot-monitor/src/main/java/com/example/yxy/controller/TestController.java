package com.example.yxy.controller;

import com.example.yxy.entity.MyUser;
import com.example.yxy.service.MyUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MyUserService myUserService;

    /**
     * @return {@link String}
     */
    @GetMapping("/saveUser")
    public String saveUser(@RequestBody MyUser myUser){
        myUserService.saveUser(myUser);
        return "ok";
    }

    /**
     * 死循环
     * @return {@link String}
     */
    @GetMapping("/endlessLoop")
    public String endlessLoop() throws InterruptedException {
        while (true){
            System.out.println("123");
            Thread.sleep(100);
        }
    }

}
