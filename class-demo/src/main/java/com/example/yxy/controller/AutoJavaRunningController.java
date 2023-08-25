package com.example.yxy.controller;

import com.example.yxy.config.exec.MethodExecUtils;
import com.example.yxy.config.java.running.JavaRunningFactory;
import com.example.yxy.entity.user.io.UserInfoIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 动态字节码执行
 *
 * @author yxy
 * @date 2023/08/25
 */
@Slf4j
@RestController
@RequestMapping("/autoJava")
@CrossOrigin
public class AutoJavaRunningController {

    @PostMapping("/insert")
    public String search(@RequestBody String javaCode) throws Exception {
        JavaRunningFactory.loadNewInstance2Method(javaCode);
        return "ok";
    }

}
