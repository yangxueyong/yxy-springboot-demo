package com.example.yxy.service;

import com.alibaba.fastjson2.JSON;
import com.example.yxy.config.CustomLogAnn;
import com.example.yxy.entity.TestAutoIdEntity;
import com.example.yxy.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public interface TestService {

    List selectTimeOut();

    TestAutoIdEntity saveReturnPK(TestAutoIdEntity testAutoIdEntity);

    void testAnn1();

    void testAnn2();
}
