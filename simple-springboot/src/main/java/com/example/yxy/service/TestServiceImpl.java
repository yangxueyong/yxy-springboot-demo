package com.example.yxy.service;

import com.alibaba.fastjson2.JSON;
import com.example.yxy.config.CustomLogAnn;
import com.example.yxy.entity.TestAutoIdEntity;
import com.example.yxy.entity.TestAutoIdEntity2;
import com.example.yxy.mapper.TestMapper;
import com.example.yxy.mapper.TestMapper2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class TestServiceImpl  implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TestMapper2 testMapper2;

    @Transactional
    public void selectInt() {
        int i = testMapper.selectInt();
        System.out.println(JSON.toJSONString(i));
    }

    public List selectTimeOut(){
        HashMap param = new HashMap<>();
        param.put("page1",new Random().nextInt(1000));
        param.put("page2",new Random().nextInt(1000));
        param.put("age1",new Random().nextInt(1000));
        param.put("age2",new Random().nextInt(1000));
        return testMapper.selectTimeOut(param);
    }
    public TestAutoIdEntity saveReturnPK(TestAutoIdEntity testAutoIdEntity){
        testMapper.saveReturnPK(testAutoIdEntity);
        return testAutoIdEntity;
    }

    public TestAutoIdEntity2 saveReturnPK2(TestAutoIdEntity2 testAutoIdEntity){
        testMapper2.insert(testAutoIdEntity);
        return testAutoIdEntity;
    }

    public void testAnn1(){
        log.info("这是第1个方法1");
        testAnn2();
        ((TestService)AopContext.currentProxy()).testAnn2();
    }

    @CustomLogAnn
    public void testAnn2(){
        log.info("这是第2个方法2");
    }
}
