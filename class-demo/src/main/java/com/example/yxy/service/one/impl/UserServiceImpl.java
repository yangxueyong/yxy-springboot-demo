package com.example.yxy.service.one.impl;

import com.example.yxy.config.annotation.ESSearchTaskAnnotation;
import com.example.yxy.entity.user.vo.UserInfoAddressVO;
import com.example.yxy.entity.user.vo.UserInfoVO;
import com.example.yxy.service.one.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Override
    @ESSearchTaskAnnotation(searchId = "user", type = "queryUserInfo")
    public UserInfoVO queryUserInfo(Map resultMap,String param) {
        UserInfoVO user = UserInfoVO.builder()
                .userId("xx1")
                .userName("张三")
                .build();
        resultMap.put("userInfo",user);
        return user;
    }

    @Override
    @ESSearchTaskAnnotation(searchId = "user", type = "queryUserAddress")
    public List<UserInfoAddressVO> queryUserAddress(Map resultMap,String param) {
        List<UserInfoAddressVO> userInfoAddressVOS = Arrays.asList(UserInfoAddressVO.builder()
                        .userId("xx1")
                        .phone("1300000000000")
                        .address("重庆市九龙坡区xxx").build(),

                UserInfoAddressVO.builder()
                        .userId("xx1")
                        .phone("1700000000000")
                        .address("重庆市大渡口区xxx").build()
        );
        resultMap.put("userAddress",userInfoAddressVOS);
        return userInfoAddressVOS;
    }
}
