package com.example.yxy.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class MyErrorDelegate2 implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("这是一个自定义的Error2 " + LocalDateTime.now().toString());
    }
}
