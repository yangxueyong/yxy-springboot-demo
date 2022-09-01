package com.example.yxy.config.spring;

import com.example.yxy.config.annotation.ESSearchTaskAnnotation;
import com.example.yxy.config.exec.MethodExecUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        MethodExecUtils.getScheduleMethodMap(ESSearchTaskAnnotation.class);
    }
}
