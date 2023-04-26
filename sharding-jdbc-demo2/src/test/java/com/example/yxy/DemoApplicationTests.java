package com.example.yxy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yxy.entity.MyNoSplitTab;
import com.example.yxy.entity.RedAccount;
import com.example.yxy.entity.io.RedAccountIO;
import com.example.yxy.mapper.MyNoSplitTabMapper;
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
    @Autowired
    private MyNoSplitTabMapper myNoSplitTabMapper;

    /**
     * 保存数据到交易库
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
     * 保存历史数据,由于没有设置AcctNo的值，所有，历史库每个表都会存储数据
     */
    @Test
    void saveHisData() {
        RedAccount redAccount = new RedAccount();
        redAccount.setActNo("活动号1");
        redAccount.setActType("bb");
        redAccount.setTranDay("2022-02-02");
        redAccount.setCreateTime(new Date());
        redAccount.setMainProNo("main1");
        redAccount.setSubProNo("sub1");
        redAccountMapper.insert(redAccount);
    }

    /**
     * 按照精确分片规则 查询历史库（今年为2023年）
     * 由于指定了时间和acctNo因此，只会精确查询一个表
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
     * 按照精确分片规则 查询历史库（今年为2023年）
     * 由于指定了时间和acctNo因此，只会精确查询一个表
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
     * 根据acct_no分片规则（hashcode），会落到某个具体的表，
     * 由于没有指定时间，因此不知道具体查询哪个库，所以两个库都会查询
     */
    @Test
    void queryAccountRed() {
        QueryWrapper<RedAccount> wrapper = new QueryWrapper<RedAccount>();
        wrapper.eq("ACCT_NO","kkq");
        List<RedAccount> bankFlow = redAccountMapper.selectList(wrapper);
        System.out.println( bankFlow);
    }


    /**
     * 按照范围分片规则 查询交易库（今年为2023年）
     * 这个会查询交易库的所有表
     */
    @Test
    void queryAllData() {
        RedAccountIO io = new RedAccountIO();
        io.setTranDay("2023-01-01");
        List<RedAccount> redAccounts = redAccountMapper.selectByRangeKey(io);
        System.out.println( redAccounts);
    }

    /**
     * 不走分片规则的普通表
     */
    @Test
    void queryNoSplitTab() {
        QueryWrapper<MyNoSplitTab> wrapper = new QueryWrapper<MyNoSplitTab>();
        wrapper.eq("id","100");
        List<MyNoSplitTab> myNoSplitTabs = myNoSplitTabMapper.selectList(wrapper);
        System.out.println(myNoSplitTabs);
    }

}