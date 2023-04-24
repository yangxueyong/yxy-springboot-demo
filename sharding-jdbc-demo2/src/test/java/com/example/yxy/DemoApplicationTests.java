package com.example.yxy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yxy.entity.RedAccount;
import com.example.yxy.mapper.RedAccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
 
 
    @Autowired
    private RedAccountMapper redAccountMapper;

    @Test
    void saveNowData() {
        RedAccount redAccount = new RedAccount();
        redAccount.setAcctNo("sdhfkjsdfksdf");
        redAccount.setActNo("活动号1");
        redAccount.setActType("bb");
        redAccount.setTranDay("2023-02-02");
        redAccount.setCreateTime(new Date());
        redAccount.setMainProNo("main1");
        redAccount.setSubProNo("sub1");
        redAccountMapper.insert(redAccount);
    }

    @Test
    void saveHisData() {
        RedAccount redAccount = new RedAccount();
        redAccount.setAcctNo("ashksdhfkdsf");
        redAccount.setActNo("活动号1");
        redAccount.setActType("bb");
        redAccount.setTranDay("2022-02-02");
        redAccount.setCreateTime(new Date());
        redAccount.setMainProNo("main1");
        redAccount.setSubProNo("sub1");
        redAccountMapper.insert(redAccount);
    }

    @Test
    void queryData() {
        QueryWrapper<RedAccount> wrapper = new QueryWrapper<RedAccount>();
        wrapper.eq("ACCT_NO","kkq");
        List<RedAccount> bankFlow = redAccountMapper.selectList(wrapper);
        System.out.println( bankFlow);
    }

}