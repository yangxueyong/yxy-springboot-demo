package com.example.yxy.service;

import com.alibaba.fastjson2.JSON;
import com.example.yxy.entity.TestAutoIdEntity;
import com.example.yxy.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;

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
}
