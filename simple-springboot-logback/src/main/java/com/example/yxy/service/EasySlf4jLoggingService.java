package com.example.yxy.service;

import com.example.yxy.entity.log.EasySlf4jLogging;
import com.example.yxy.mapper.EasySlf4jLoggingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EasySlf4jLoggingService {
    @Autowired
    private EasySlf4jLoggingMapper testMapper;

    public void asyncSave(EasySlf4jLogging log) {
        testMapper.insert(log);
    }
}
