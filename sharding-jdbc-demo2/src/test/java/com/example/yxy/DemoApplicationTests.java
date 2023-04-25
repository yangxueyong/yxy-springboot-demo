package com.example.yxy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yxy.entity.RedAccount;
import com.example.yxy.entity.io.RedAccountIO;
import com.example.yxy.mapper.RedAccountMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {
 
 
    @Autowired
    private RedAccountMapper redAccountMapper;

    /**
     * 保存交易数据
     */
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

    /**
     * 保存历史数据
     */
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

    /**
     * 现在交易库数据
     */
    @Test
    void queryNowData() {
        QueryWrapper<RedAccount> wrapper = new QueryWrapper<RedAccount>();
        wrapper.eq("ACCT_NO","kkq");
        List<RedAccount> bankFlow = redAccountMapper.selectList(wrapper);
        System.out.println( bankFlow);
    }

    /**
     * 查询历史数据
     */
    @Test
    void queryHisData() {
        QueryWrapper<RedAccount> wrapper = new QueryWrapper<RedAccount>();
        wrapper.eq("ACCT_NO","kkq");
        wrapper.eq("TRAN_DAY","2022-02-02");
        List<RedAccount> bankFlow = redAccountMapper.selectList(wrapper);
        System.out.println( bankFlow);
    }

    /**
     * 查询历史数据
     */
    @Test
    void queryHisData2() {
        RedAccountIO io = new RedAccountIO();
        io.setAcctNo("kkq");
        io.setTranDay("2022-02-02");
        List<RedAccount> redAccounts = redAccountMapper.selectByKey(io);
        System.out.println( redAccounts);
    }



    /**
     * 查询所有数据
     */
    @Test
    void queryAllData() {
        RedAccountIO io = new RedAccountIO();
        io.setTranDay("2023-01-01");
        List<RedAccount> redAccounts = redAccountMapper.selectByRangeKey(io);
        System.out.println( redAccounts);
    }

}