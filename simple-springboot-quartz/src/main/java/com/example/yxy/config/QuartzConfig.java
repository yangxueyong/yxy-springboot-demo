package com.example.yxy.config;


//import com.mes.material.constants.MaterialConstants;
//import com.mes.material.quartz.factory.QuartzJobFactory;
//import com.mes.material.quartz.listener.SimpleJobListener;
//import com.mes.material.quartz.listener.SimpleSchedulerListener;
//import com.mes.material.quartz.listener.SimpleTriggerListener;
import com.example.yxy.util.MaterialConstants;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
 
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
 
/** @Author: 
 * @Description: 3.5、注册调度工厂
 * @Date: 15:42 2022/8/10
 * @Param
 * @return
 **/
 
@Configuration
public class QuartzConfig {
 
    @Autowired
    private QuartzJobFactory jobFactory;
 
    @Autowired
    private SimpleSchedulerListener simpleSchedulerListener;
 
    @Autowired
    private SimpleJobListener simpleJobListener;
 
    @Autowired
    private SimpleTriggerListener simpleTriggerListener;
    @Autowired
    private DataSource dataSource;
 
    @Value("${org.quartz.scheduler.instanceName}")
    private String instanceName;
    @Value("${org.quartz.scheduler.instanceId}")
    private String instanceId;
    @Value("${org.quartz.threadPool.class}")
    private String threadPoolClass;
    @Value("${org.quartz.threadPool.threadCount}")
    private String threadCount;
    @Value("${org.quartz.threadPool.threadPriority}")
    private String threadPriority;
    @Value("${org.quartz.jobStore.class}")
    private String jobStoreClass;
    @Value("${org.quartz.jobStore.isClustered}")
    private String isClustered;
    @Value("${org.quartz.jobStore.clusterCheckinInterval}")
    private String clusterCheckinInterval;
    @Value("${org.quartz.jobStore.maxMisfiresToHandleAtATime}")
    private String maxMisfiresToHandleAtATime;
    @Value("${org.quartz.jobStore.misfireThreshold}")
    private String misfireThreshold;
    @Value("${org.quartz.jobStore.tablePrefix}")
    private String tablePrefix;
    @Value("${org.quartz.jobStore.acquireTriggersWithinLock}")
    private String acquireTriggersWithinLock;
    @Value("${org.quartz.jobStore.driverDelegateClass}")
    private String driverDelegateClass;
    @Value("${org.quartz.jobStore.dataSource}")
    private String dataSourceName;
    @Value("${org.quartz.dataSource.qzDS.connectionProvider.class}")
    private String connectionProviderClass;
    @Value("${org.quartz.dataSource.qzDS.driver}")
    private String driver;
    @Value("${org.quartz.dataSource.qzDS.URL}")
    private String url;
    @Value("${org.quartz.dataSource.qzDS.user}")
    private String user;
    @Value("${org.quartz.dataSource.qzDS.password}")
    private String password;
    @Value("${org.quartz.dataSource.qzDS.maxConnection}")
    private String maxConnection;
    @Value("${org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread}")
    private String threadsInheritContextClassLoaderOfInitializingThread;
 
 
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        //创建SchedulerFactoryBean
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
 
        //quartz参数
        Properties prop = new Properties();
        //调度器实例名称
        prop.put("org.quartz.scheduler.instanceName", instanceName);
        //调度器实例编号自动生成
        prop.put("org.quartz.scheduler.instanceId", instanceId);
        //线程池配置--线程池的实现类
        prop.put("org.quartz.threadPool.class", threadPoolClass);
        //线程池中的线程数量
        prop.put("org.quartz.threadPool.threadCount", threadCount);
        //配置是否启动自动加载数据库内的定时任务，默认true
        prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", threadsInheritContextClassLoaderOfInitializingThread);
        //线程优先级
        prop.put("org.quartz.threadPool.threadPriority", threadPriority);
        //JobStore配置-数据保存方式为数据库持久化
        prop.put("org.quartz.jobStore.class", jobStoreClass);
        //集群配置--是否以集群方式运行
        prop.put("org.quartz.jobStore.isClustered", isClustered);
        //调度实例失效的检查时间间隔，单位毫秒
        prop.put("org.quartz.jobStore.clusterCheckinInterval", clusterCheckinInterval);
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", maxMisfiresToHandleAtATime);
        //最大能忍受的触发超时时间
        prop.put("org.quartz.jobStore.misfireThreshold", misfireThreshold);
        //数据表的前缀，默认QRTZ_
        prop.put("org.quartz.jobStore.tablePrefix", tablePrefix);
        //是否开启悲观锁控制集群中trigger并发
        prop.put("org.quartz.jobStore.acquireTriggersWithinLock", acquireTriggersWithinLock);
 
        //数据库代理类，一般org.quartz.impl.jdbcjobstore.StdJDBCDelegate可以满足大部分数据库
        prop.put("org.quartz.jobStore.driverDelegateClass", driverDelegateClass);
        //数据库别名 随便取
        prop.put("org.quartz.jobStore.dataSource",dataSourceName);
        //数据库连接池，将其设置为druid
        prop.put("org.quartz.dataSource.qzDS.connectionProvider.class",connectionProviderClass);
        //数据库引擎
        prop.put("org.quartz.dataSource.qzDS.driver",driver);
        //数据库连接
        prop.put("org.quartz.dataSource.qzDS.URL",url);
        //数据库用户
        prop.put("org.quartz.dataSource.qzDS.user",user);
        //数据库密码
        prop.put("org.quartz.dataSource.qzDS.password",password);
        //允许最大连接
        prop.put("org.quartz.dataSource.qzDS.maxConnection",maxConnection);
 
        factory.setQuartzProperties(prop);
 
        //支持在JOB实例中注入其他的业务对象
        factory.setJobFactory(jobFactory);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        //这样当spring关闭时，会等待所有已经启动的quartz job结束后spring才能完全shutdown。
        factory.setWaitForJobsToCompleteOnShutdown(true);
        //是否覆盖己存在的Job
        factory.setOverwriteExistingJobs(false);
        //QuartzScheduler 延时启动，应用启动完后 QuartzScheduler 再启动
        factory.setStartupDelay(10);
        //使用数据源，自定义数据源(采用项目数据源)
        //在quartz.properties配置文件中，去掉org.quartz.jobStore.dataSource配置
//        factory.setDataSource(dataSource);
        return factory;
    }
 
    /**
     * 通过SchedulerFactoryBean获取Scheduler的实例
     * @return
     * @throws IOException
     * @throws SchedulerException
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler() throws IOException, SchedulerException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        //全局添加监听器
        //添加SchedulerListener监听器
        scheduler.getListenerManager().addSchedulerListener(simpleSchedulerListener);
 
        // 添加JobListener, 支持带条件匹配监听器
        scheduler.getListenerManager().addJobListener(simpleJobListener, KeyMatcher.keyEquals(JobKey.jobKey(MaterialConstants.QURATZ_NAME,MaterialConstants.QURATZ_GROUP_NAME)));
 
        // 添加triggerListener，设置全局监听
        scheduler.getListenerManager().addTriggerListener(simpleTriggerListener, EverythingMatcher.allTriggers());
        return scheduler;
    }
}