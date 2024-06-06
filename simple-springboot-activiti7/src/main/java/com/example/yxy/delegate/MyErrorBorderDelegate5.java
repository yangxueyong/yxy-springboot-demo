package com.example.yxy.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class MyErrorBorderDelegate5 implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("error服务 5 " + execution.getEventName() + "==" + LocalDateTime.now().toString());
    }
}
