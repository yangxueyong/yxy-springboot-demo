package com.example.yxy.service.one.impl;

import com.example.yxy.config.SpringUtil;
import com.example.yxy.service.one.UploadCDNService;
import com.example.yxy.service.two.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;

public class ChinaNetCDNImpl implements UploadCDNService {//上传网宿cdn
    @Autowired
    private UserService userService;

    @Override
    public void upload(String url) {
        UserService userService1 = SpringUtil.getBean(UserService.class);
        userService1.sayHello();
        System.out.println("upload to chinaNet cdn");
    }
}
