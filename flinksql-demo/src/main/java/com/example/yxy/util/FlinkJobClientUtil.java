package com.example.yxy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.JobStatus;
import org.apache.flink.client.deployment.StandaloneClusterId;
import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.client.program.PackagedProgramUtils;
import org.apache.flink.client.program.rest.RestClusterClient;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.flink.runtime.messages.Acknowledge;
import org.apache.flink.runtime.rest.messages.job.JobDetailsInfo;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * @ClassName FlinkClient
 * @Description TODO
 * @Author Getech
 * @Date 2021/6/24 17:59
 */
@Slf4j
@Component
public class FlinkJobClientUtil {


    private static Configuration flinkConfig;

    private static RestClusterClient flinkClient;

    static{
        flinkConfig = new Configuration();
        flinkConfig.setString(JobManagerOptions.ADDRESS, "127.0.0.1");
        flinkConfig.setInteger(JobManagerOptions.PORT, 6123);
        flinkConfig.setInteger(RestOptions.PORT, 8081);

        try {
            flinkClient = new RestClusterClient<StandaloneClusterId>(flinkConfig, StandaloneClusterId.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 提交flink任务入口方法
     *
     * 应用将flink建表语句和flink执行语句封装到文件中 传给jar包 ->
     * jar包中的 main方法解析文件中的sql，拿到建表语句和执行语句 ，预执行，得到执行计划 ->
     * 应用将执行计划提交给flink集群
     *
     * @param filePath 文件路径
     * @return {@link String}
     * @throws Exception 异常
     */
    public String submitStreamFlinkJob(String filePath) throws Exception {
        try {
            File jarFile = new File("/Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/flinksql-task-demo/target/flinksql-task-demo-1.0.0-jar-with-dependencies.jar");
            SavepointRestoreSettings savepointRestoreSettings = SavepointRestoreSettings.none();
            PackagedProgram program = PackagedProgram.newBuilder()
                    .setConfiguration(flinkConfig)
                    .setEntryPointClassName("com.example.yxy.FlinkTaskMain")
                    .setJarFile(jarFile)
                    .setSavepointRestoreSettings(savepointRestoreSettings)
                    .setArguments(filePath)
                    .build();

            JobGraph jobGraph = PackagedProgramUtils.createJobGraph(program, flinkConfig, 1, false);
            CompletableFuture<JobID> result = flinkClient.submitJob(jobGraph);
            JobID jobId = result.get();
            log.info("提交完成,jobId:" + jobId.toString());
            return jobId.toString();
        } catch (Exception e) {
            log.error("报错了->", e);
            throw e;
        }
    }

    /**
     * 取消flink job
     *
     * @param jobId jobid
     * @return {@link JobDetailsInfo}
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    public JobDetailsInfo cancelJobById(String jobId) throws ExecutionException, InterruptedException {
        JobDetailsInfo jobDetailsInfo = getFlinkJobDetail(jobId);
        if (jobDetailsInfo == null) {
            return null;
        }
        JobStatus jobStatus = jobDetailsInfo.getJobStatus();
        if (!JobStatus.FAILING.equals(jobStatus)
                && !JobStatus.FAILED.equals(jobStatus)
                && !JobStatus.CANCELLING.equals(jobStatus)
                && !JobStatus.CANCELED.equals(jobStatus)
                && !JobStatus.FINISHED.equals(jobStatus)) {
            CompletableFuture<Acknowledge> cancel = null;

            cancel = flinkClient.cancel(JobID.fromHexString(jobId));

            Acknowledge acknowledge = cancel.get();
            log.info("结果->{}", acknowledge.toString());
        }
        return getFlinkJobDetail(jobId);
    }

    /**
     * 获取flink job的详情
     *
     * @param jobId 工作id
     * @return {@link JobDetailsInfo}
     */
    public JobDetailsInfo getFlinkJobDetail(String jobId) {
        try {
            // 集群信息
            CompletableFuture<JobDetailsInfo> jobDetails =  flinkClient.getJobDetails(JobID.fromHexString(jobId));
            return jobDetails.get();
        } catch (Exception e) {
            log.error("获取job失败->", e);
        }
        return null;
    }


}