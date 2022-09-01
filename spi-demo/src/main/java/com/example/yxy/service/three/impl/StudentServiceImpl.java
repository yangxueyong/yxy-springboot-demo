package com.example.yxy.service.three.impl;

import com.example.yxy.service.one.UploadCDNService;
import com.example.yxy.service.three.ScoreService;
import com.example.yxy.service.three.StudentService;
import com.example.yxy.service.two.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private ScoreService scoreService;

    @Override
    public void sayHello() {
        log.info("my student->>>>>>>>>>>>>>>>>>>>>>>>>>");
        scoreService.stuScore();
    }
}

