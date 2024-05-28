package com.example.yxy.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class Listener01 implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Listener01: TaskId: " + delegateTask.getId());
        System.out.println("Listener01: TaskName: " + delegateTask.getName() );
    }
}
