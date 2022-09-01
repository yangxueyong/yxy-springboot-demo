package com.example.yxy.service.one;

import com.example.yxy.entity.user.vo.UserInfoAddressVO;
import com.example.yxy.entity.user.vo.UserInfoVO;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserInfoVO queryUserInfo(Map resultMap,String param);

    List<UserInfoAddressVO> queryUserAddress(Map resultMap,String param);
}
