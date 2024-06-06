package com.example.yxy.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class MySignalMiddleDelegate1 implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("signalMiddle服务 " + execution.getEventName() + "==" + LocalDateTime.now().toString());
    }
}
