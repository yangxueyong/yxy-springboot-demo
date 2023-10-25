package com.example.yxy.service.three.impl;

import com.example.yxy.service.three.ScoreService;
import com.example.yxy.service.three.StudentService;
import com.example.yxy.service.two.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private UserService userService;

    @Override
    public void stuScore() {
        log.info("stu score->>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}

