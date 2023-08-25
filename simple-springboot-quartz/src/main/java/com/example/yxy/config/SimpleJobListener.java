package com.example.yxy.config;
 
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.stereotype.Component;
 
/**
 * @Author: 
 * @Description: 任务执行监听器
 * @Date Create in 14:49 2022/5/16
 * @Modified By:
 */
 
@Component
public class SimpleJobListener extends JobListenerSupport {
 
 
    /**
     * job监听器名称
     * @return
     */
    @Override
    public String getName() {
        return "mySimpleJobListener";
    }
 
    /**
     * 任务被调度前
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("simpleJobListener监听器，准备执行："+context.getJobDetail().getKey());
    }
 
    /**
     * 任务调度被拒了
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("simpleJobListener监听器，取消执行："+context.getJobDetail().getKey());
    }
 
    /**
     * 任务被调度后
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("simpleJobListener监听器，执行结束："+context.getJobDetail().getKey());
    }
}