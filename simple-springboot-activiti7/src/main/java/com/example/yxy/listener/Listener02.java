package com.example.yxy.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class Listener02 implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Listener02: TaskId: " + delegateTask.getId());
        System.out.println("Listener02: TaskName: " + delegateTask.getName() );
    }
}
