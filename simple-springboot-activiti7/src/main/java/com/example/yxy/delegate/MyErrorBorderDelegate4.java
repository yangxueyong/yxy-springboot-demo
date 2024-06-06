package com.example.yxy.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class MyErrorBorderDelegate4 implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("error服务 4 " + execution.getEventName() + "==" + LocalDateTime.now().toString());
    }
}
