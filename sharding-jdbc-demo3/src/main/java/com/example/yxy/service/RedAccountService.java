package com.example.yxy.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.yxy.entity.MyNoSplitTab;
import com.example.yxy.entity.RedAccount;
import com.example.yxy.mapper.MyNoSplitTabMapper;
import com.example.yxy.mapper.RedAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RedAccountService {
    @Autowired
    private RedAccountMapper redAccountMapper;
    @Autowired
    private MyNoSplitTabMapper myNoSplitTabMapper;

    @Transactional
    public void saveTran(String date) {
        RedAccount redAccount = new RedAccount();
        String acctNo = RandomUtil.randomString(18);
        redAccount.setAcctNo(acctNo);
        redAccount.setActNo("活动号8");
        redAccount.setActType("zz");
        Date createTime = new Date();
        createTime.setTime(RandomUtil.randomLong(1620122215,1683194215) * 1000);
        redAccount.setTranDay(date);
        redAccount.setCreateTime(createTime);
        redAccount.setMainProNo("main1");
        redAccount.setSubProNo("sub5");
        redAccountMapper.insert(redAccount);

        MyNoSplitTab myNoSplitTab = new MyNoSplitTab();
        myNoSplitTab.setId(acctNo);
        myNoSplitTabMapper.insert(myNoSplitTab);
        //随机报错
        int i1 = RandomUtil.randomInt(0, 10);
        if(i1 < 5) {
            int i = 1 / 0;
        }
    }
}
