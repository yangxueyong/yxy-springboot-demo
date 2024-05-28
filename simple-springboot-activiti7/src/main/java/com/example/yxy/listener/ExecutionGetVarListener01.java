package com.example.yxy.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 获取上一个节点的变量并打印
 * @author yxy
 * @date 2024/05/28
 */
public class ExecutionGetVarListener01 implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("ExecutionGetVarListener01 getVariable: " + delegateExecution.getVariable("day"));
        System.out.println("ExecutionGetVarListener01 getEventName: " + delegateExecution.getEventName());
        System.out.println("ExecutionGetVarListener01 isActive: " + delegateExecution.isActive());
    }
}
