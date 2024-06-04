package com.example.yxy.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class MyDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("这是一个自定义的JavaDelegate " + LocalDateTime.now().toString());
    }
}
