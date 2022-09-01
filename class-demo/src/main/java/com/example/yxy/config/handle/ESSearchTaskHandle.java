package com.example.yxy.config.handle;

import java.util.concurrent.CountDownLatch;

@FunctionalInterface
public
interface ESSearchTaskHandle {
    /**
     * 执行内容
     */
    void execute();

    default void doExecForCD(CountDownLatch cd){
        try{
            execute();
        }catch (Exception e){
            throw e;
        }finally {
            cd.countDown();
        }
    }

    default void doExec(){
        try{
            execute();
        }catch (Exception e){
            throw e;
        }
    }
}