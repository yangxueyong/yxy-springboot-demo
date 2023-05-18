package com.example.yxy.service;

import com.alibaba.fastjson2.JSON;
import com.example.yxy.entity.Test;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    public void saveBatch(List<T> datas){
        System.out.println(JSON.toJSONString(datas));
    }
}
