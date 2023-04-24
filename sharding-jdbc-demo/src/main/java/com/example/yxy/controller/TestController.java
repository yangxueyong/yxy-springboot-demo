package com.example.yxy.controller;

import com.example.yxy.entity.BankFlow;
import com.example.yxy.mapper.BankFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private BankFlowMapper bankFlowMapper;

    @RequestMapping("/insert")
    private String insert() throws Exception {
        BankFlow bankFlow = new BankFlow();
        bankFlow.setId(100L);
        bankFlow.setCreateTime(Instant.now());
        bankFlow.setFlowTime(Instant.now());
        bankFlow.setFlowId("ceshi");
        bankFlow.setMoney(new BigDecimal("888.88"));
        bankFlow.setShardingTime(System.currentTimeMillis());
        bankFlowMapper.insert(bankFlow);
        return "ok";
    }
}
