package com.example.yxy.service.two.impl;

import com.example.yxy.service.two.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class UserServiceImpl implements UserService {
    @Override
    public void sayHello() {
        log.info("hello->>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}

