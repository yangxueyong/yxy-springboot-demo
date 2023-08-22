package com.example.yxy.service;

//import com.example.yxy.entity.log.EasySlf4jLogging;
import com.example.yxy.entity.MyUser;
import com.example.yxy.mapper.MyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {
    @Autowired
    private MyUserMapper testMapper;

    public void saveUser(MyUser log) {
        testMapper.insert(log);
    }
}
