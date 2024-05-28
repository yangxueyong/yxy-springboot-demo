package com.example.yxy.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ExecutionListener02 implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("ExecutionListener02 getEventName: " + delegateExecution.getEventName());
        System.out.println("ExecutionListener02 isActive: " + delegateExecution.isActive());
    }
}
