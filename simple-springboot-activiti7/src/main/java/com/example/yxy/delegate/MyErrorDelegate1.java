package com.example.yxy.delegate;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class MyErrorDelegate1 implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("这是一个自定义的Error1 " + LocalDateTime.now().toString());
        throw new BpmnError("error1");
    }
}
