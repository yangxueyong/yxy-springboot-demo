package com.example.yxy.service.activiti7;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

@Service
public class MyTaskService {

    public void isPm(DelegateExecution delegateExecution) {
        String creator = (String)delegateExecution.getVariable("creator");
        //是否经理请假
        if ("manager".equals(creator)) {
            delegateExecution.setVariable("isPM", 1);
        } else {
            delegateExecution.setVariable("isPM", 0);
        }
    }
}
