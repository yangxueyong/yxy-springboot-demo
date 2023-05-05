package com.example.yxy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yxy.entity.MyNoSplitTab;
import com.example.yxy.entity.RedAccount;
import com.example.yxy.entity.io.RedAccountIO;
import com.example.yxy.mapper.MyNoSplitTabMapper;
import com.example.yxy.mapper.RedAccountMapper;
import com.example.yxy.service.RedAccountService;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
 
 
    @Autowired
    private RedAccountMapper redAccountMapper;
    @Autowired
    private MyNoSplitTabMapper myNoSplitTabMapper;

    @Autowired
    private RedAccountService redAccountService;
    /**
     * 保存数据到交易库
     */
    @Test
    void saveNowData() {
        for (int i = 0; i < 100; i++) {
            System.out.println("i-->" + i);
            RedAccount redAccount = new RedAccount();
            redAccount.setAcctNo(RandomUtil.randomString(18));
            redAccount.setActNo("活动号3");
            redAccount.setActType("kk");
            Date createTime = new Date();
            createTime.setTime(RandomUtil.randomLong(1620122215,1683194215) * 1000);
            redAccount.setTranDay(DateUtil.format(createTime,"yyyy-MM-dd"));
            redAccount.setCreateTime(createTime);
            redAccount.setMainProNo("main1");
            redAccount.setSubProNo("sub3");
            redAccountMapper.insert(redAccount);
        }
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
    void queryNowData() {
        QueryWrapper<RedAccount> wrapper = new QueryWrapper<RedAccount>();
        wrapper.eq("ACCT_NO","3ghfm25wuo7s1eyjho");
        wrapper.eq("TRAN_DAY","2023-01-01");
        List<RedAccount> bankFlow = redAccountMapper.selectList(wrapper);
        System.out.println( bankFlow);
    }

    /**
     * 按照精确分片规则 查询历史库（今年为2023年）
     * 由于指定了时间和acctNo因此，只会精确查询一个表
     */
    @Test
    void queryHisData() {
        QueryWrapper<RedAccount> wrapper = new QueryWrapper<RedAccount>();
        wrapper.eq("ACCT_NO","6pyq9g1avaiird0c3u");
        wrapper.eq("TRAN_DAY","2022-03-12");
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
        io.setAcctNo("6pyq9g1avaiird0c3u");
        io.setTranDay("2022-03-12");
        List<RedAccount> redAccounts = redAccountMapper.selectByKey(io);
        System.out.println( redAccounts);
    }


    /**
     * 根据acct_no分片规则（hashcode），会落到某个具体的表，
     * 由于没有指定时间，因此不知道具体查询哪个库，所以两个库都会查询
     *
     * 排序加分页 这里是根据时间升序排序，理论上应该是优先返回bankhis的数据，但是这里的查询会优先查询banknow 所以会优先返回banknow的数据
     */
    @Test
    void queryAccountRed() {
        QueryWrapper<RedAccount> wrapper = new QueryWrapper<RedAccount>();
        wrapper.eq("ACCT_NO","cej9npgyucmym1zs67");
        wrapper.orderByAsc("TRAN_DAY");
        PageHelper.startPage(1, 3);
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
     * 按照范围分片规则 查询交易库（今年为2023年）
     * 这个会查询交易库的所有表
     */
    @Test
    void selectByInnerJoin() {
        RedAccountIO io = new RedAccountIO();
        io.setTranDay("2023-01-01");
        List<RedAccount> redAccounts = redAccountMapper.selectByInnerJoin(io);
        System.out.println( redAccounts);
    }

    /**
     * 按照范围分片规则 查询交易库（今年为2023年）
     * 这个会查询交易库的所有表 分页
     */
    @Test
    void selectByInnerJoinPage() {
        RedAccountIO io = new RedAccountIO();
        io.setTranDay("2023-01-01");
        PageHelper.startPage(1, 3);
        List<RedAccount> redAccounts = redAccountMapper.selectByInnerJoin(io);
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

    /**
     * 事务测试
     * 保存到bankNow
     */
    @Test
    void saveNowTran() {
        for (int i = 0; i < 5; i++) {
            try {
                redAccountService.saveTran("2023-05-05");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 事务测试
     * 保存到bankhis
     */
    @Test
    void saveHisTran() {
        for (int i = 0; i < 5; i++) {
            try {
                redAccountService.saveTran("2022-05-05");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}