package com.example.yxy.job;
 
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.impl.JobDetailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Author: 
 * @Description: 3.7、编写 Job 具体任务类
 * @Date Create in 15:43 2022/8/10
 * @Modified By:
 */
 
//此标记用在实现Job的类上面,意思是不允许并发执行,按照我之前的理解是不允许调度框架在同一时刻调用Job类，后来经过测试发现并不是这样，
// 而是Job(任务)的执行时间[比如需要10秒]大于任务的时间间隔[Interval（5秒)],那么默认情况下,调度框架为了能让
//任务按照我们预定的时间间隔执行,会马上启用新的线程执行任务。否则的话会等待任务执行完毕以后再重新执行！（这样会导致任务的执行不是按照我们预先定义的时间间隔执行）
//测试代码，这是官方提供的例子。设定的时间间隔为3秒,但job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行，
// 否则会在3秒时再启用新的线程执行
 
@DisallowConcurrentExecution
public class WasCommandJob implements Job {
 
//    @Autowired
//    private StrategyService strategyService;
 
    private static final Logger log = LoggerFactory.getLogger(WasCommandJob.class);
 
    private static final String lock_key = "material_from_eam_info";
 
    private static final String lock_value = "material_from_eam_info_zkaw";
 
//    @Value("${schedule.expire}")
//    private long timeOut;
 
//    @Autowired
//    private RedisTemplate redisTemplate;
 
    @Override
    public void execute(JobExecutionContext context) {
        try {
            //分布式锁
            boolean lock = false;
            try {
                JobDetailImpl jobDetail = (JobDetailImpl)context.getJobDetail();
                log.info("group-->{},name->{}", jobDetail.getGroup() ,jobDetail.getName());
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(()->{
                    try {
                        Thread.sleep(15L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
//                //如果返回true，说明key不存在，获取到锁
//                lock = redisTemplate.opsForValue().setIfAbsent(lock_key, lock_value);
//                log.info("是否获取到锁:" + lock);
//                if (lock) {
//                    log.info("获取到锁，开启定时任务！");
//                    //设置过期时间
//                    redisTemplate.expire(lock_key, timeOut, TimeUnit.SECONDS);
//                    //调用EAM接口，同步物资数据
//                    log.info("调用EAM接口，同步物资数据");
//                    String type = InterfaceTypeEnum.autoMaterial.getCode();
//                    MaterialRequestVo materialRequestVo = new MaterialRequestVo();
//                    materialRequestVo.setType(type);
//                    strategyService.render(type).materialInfoMesToRoma(materialRequestVo);
//                 } else {
//                    log.info("其他系统正在执行此项任务");
//                    return;
//                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throw new RuntimeException("分布式锁执行发生异常" + throwable.getMessage(), throwable);
            }
 
        } catch (Exception e) {
            log.error("任务执行失败",e);
        }
    }
}