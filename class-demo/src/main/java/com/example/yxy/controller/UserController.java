package com.example.yxy.controller;


import com.example.yxy.config.exec.MethodExecUtils;
import com.example.yxy.entity.user.io.UserInfoIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
* EquityRepModeController
*
* @author yangxueyong
* @date 2022-06-28 07:19:28
*/
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @PostMapping("/search")
    public Map search(@RequestBody UserInfoIO q) {
        return MethodExecUtils.search(q.getSearchId(),q.getTypes(),q.getParam());
    }

}