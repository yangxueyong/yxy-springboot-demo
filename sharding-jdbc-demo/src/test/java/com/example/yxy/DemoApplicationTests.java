package com.example.yxy;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yxy.entity.BankFlow;
import com.example.yxy.entity.MyNoSplitTab;
import com.example.yxy.mapper.BankFlowMapper;
import com.example.yxy.mapper.MyNoSplitTabMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
 
 
    @Autowired
    private BankFlowMapper bankFlowMapper;

    @Autowired
    private MyNoSplitTabMapper myNoSplitTabMapper;
 
    @Test
    void saveFlow() {
        BankFlow bankFlow = new BankFlow();
        bankFlow.setId(100L);
        bankFlow.setCreateTime(Instant.now());
        bankFlow.setFlowTime(Instant.now());
        bankFlow.setFlowId("ceshi01");
        bankFlow.setMoney(new BigDecimal("888.88"));
        DateTime date = DateUtil.parse("2022-01-02", "yyyy-MM-dd");
        bankFlow.setShardingTime(date.getTime());
        bankFlowMapper.insert(bankFlow);
    }

    @Test
    void queryFlow() {
//        BankFlow bankFlow = new BankFlow();
//        bankFlow.setId(100L);
//        bankFlow.setCreateTime(Instant.now());
//        bankFlow.setFlowTime(Instant.now());
//        bankFlow.setFlowId("ceshi");
//        bankFlow.setMoney(new BigDecimal("888.88"));
//        DateTime date = DateUtil.parse("2022-02-02", "yyyy-MM-dd");
//        bankFlow.setShardingTime(date.getTime());
//        bankFlowMapper.insert(bankFlow);

        QueryWrapper<BankFlow> wrapper = new QueryWrapper<BankFlow>();
        wrapper.eq("id",100);
        List<BankFlow> bankFlow = bankFlowMapper.selectList(wrapper);
        System.out.println( bankFlow);
    }

    @Test
    void queryGeFlow() {
//        BankFlow bankFlow = new BankFlow();
//        bankFlow.setId(100L);
//        bankFlow.setCreateTime(Instant.now());
//        bankFlow.setFlowTime(Instant.now());
//        bankFlow.setFlowId("ceshi");
//        bankFlow.setMoney(new BigDecimal("888.88"));
//        DateTime date = DateUtil.parse("2022-02-02", "yyyy-MM-dd");
//        bankFlow.setShardingTime(date.getTime());
//        bankFlowMapper.insert(bankFlow);

        QueryWrapper<BankFlow> wrapper = new QueryWrapper<BankFlow>();
        wrapper.ge("sharding_time",DateUtil.parse("2022-01-02", "yyyy-MM-dd").getTime());
        List<BankFlow> bankFlow = bankFlowMapper.selectList(wrapper);
        System.out.println( bankFlow);
    }


    @Test
    void queryFlow2() {
//        BankFlow bankFlow = new BankFlow();
//        bankFlow.setId(100L);
//        bankFlow.setCreateTime(Instant.now());
//        bankFlow.setFlowTime(Instant.now());
//        bankFlow.setFlowId("ceshi");
//        bankFlow.setMoney(new BigDecimal("888.88"));
//        DateTime date = DateUtil.parse("2022-02-02", "yyyy-MM-dd");
//        bankFlow.setShardingTime(date.getTime());
//        bankFlowMapper.insert(bankFlow);

        QueryWrapper<MyNoSplitTab> wrapper = new QueryWrapper<MyNoSplitTab>();
        wrapper.eq("id","100");
        List<MyNoSplitTab> myNoSplitTabs = myNoSplitTabMapper.selectList(wrapper);
        System.out.println(myNoSplitTabs);
    }
 
}