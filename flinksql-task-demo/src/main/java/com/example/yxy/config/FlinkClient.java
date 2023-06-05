//package com.cqrcb.cloud.config;
//import com.alibaba.fastjson2.JSON;
//import org.apache.flink.api.common.ExecutionConfig;
//import org.apache.flink.api.common.JobID;
//import org.apache.flink.api.common.JobStatus;
//import org.apache.flink.client.deployment.StandaloneClusterId;
//import org.apache.flink.client.program.PackagedProgram;
//import org.apache.flink.client.program.PackagedProgramUtils;
//import org.apache.flink.client.program.rest.RestClusterClient;
//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.configuration.JobManagerOptions;
//import org.apache.flink.configuration.RestOptions;
//import org.apache.flink.core.fs.Path;
//import org.apache.flink.runtime.client.JobStatusMessage;
//import org.apache.flink.runtime.jobgraph.JobGraph;
//import org.apache.flink.runtime.jobgraph.JobVertex;
//import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
//import org.apache.flink.runtime.jobgraph.tasks.AbstractInvokable;
//import org.apache.flink.runtime.jobmaster.JobResult;
//import org.apache.flink.runtime.rest.messages.job.JobDetailsInfo;
//import org.apache.flink.streaming.api.graph.StreamGraph;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.concurrent.CompletableFuture;
//
//
///**
// * @ClassName FlinkClient
// * @Description TODO
// * @Author Getech
// * @Date 2021/6/24 17:59
// */
//public class FlinkClient {
//
//    public static void main(String[] args) {
////        submitJob();
//        getJob("40c9942c8c910ff4a1db93fec6748e18");
//        getJob("175b1d228db72452cb41a219c0bc0c07");
//    }
//
//    public static void submitJob() {
//        String jarFilePath = "/Users/yxy/tmp/marketing-flink-task-endpoint-1.0.0-jar-with-dependencies.jar";
//        RestClusterClient<StandaloneClusterId> client = null;
//        try {
//            // 集群信息
//            Configuration configuration = new Configuration();
//            configuration.setString(JobManagerOptions.ADDRESS, "127.0.0.1");
//            configuration.setInteger(JobManagerOptions.PORT, 6123);
//            configuration.setInteger(RestOptions.PORT, 8081);
//            client = new RestClusterClient<StandaloneClusterId>(configuration, StandaloneClusterId.getInstance());
//            int parallelism = 1;
//            File jarFile=new File(jarFilePath);
//            SavepointRestoreSettings savepointRestoreSettings=SavepointRestoreSettings.none();
//            PackagedProgram program = PackagedProgram.newBuilder()
//                    .setConfiguration(configuration)
//                    .setEntryPointClassName("com.cqrcb.cloud.FlinkTaskMain")
//                    .setJarFile(jarFile)
//                    .setSavepointRestoreSettings(savepointRestoreSettings)
//                    .setArguments("/Users/yxy/tmp/171383937840410999/171385269708091399/rule.txt")
//                    .build();
//            JobGraph jobGraph=PackagedProgramUtils.createJobGraph(program,configuration,parallelism,false);
//            CompletableFuture<JobID> result = client.submitJob(jobGraph);
//            JobID jobId=  result.get();
//            System.out.println("提交完成");
//            System.out.println("jobId:"+ jobId.toString());
//            getJob(jobId.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void getJob(String jobId) {
//        RestClusterClient<StandaloneClusterId> client = null;
//        try {
//            // 集群信息
//            Configuration configuration = new Configuration();
//            configuration.setString(JobManagerOptions.ADDRESS, "127.0.0.1");
//            configuration.setInteger(JobManagerOptions.PORT, 6123);
//            configuration.setInteger(RestOptions.PORT, 8081);
//            client = new RestClusterClient<StandaloneClusterId>(configuration, StandaloneClusterId.getInstance());
//            CompletableFuture<JobStatus> jobStatus = client.getJobStatus(JobID.fromHexString(jobId));
//            System.out.println("查询完成");
//            System.out.println("jobStatus:"+ JSON.toJSONString(jobStatus.get()));
//            CompletableFuture<JobDetailsInfo> jobDetails = client.getJobDetails(JobID.fromHexString(jobId));
//            System.out.println("查询完成");
//            System.out.println("jobDetails:"+ JSON.toJSONString(jobDetails.get()));
//
//            CompletableFuture<JobResult> jobResultCompletableFuture = client.requestJobResult(JobID.fromHexString(jobId));
//            System.out.println("查询完成");
//            System.out.println("jobResultCompletableFuture:"+ JSON.toJSONString(jobResultCompletableFuture.get()));
//
//            CompletableFuture<Collection<JobStatusMessage>> collectionCompletableFuture = client.listJobs();
//            System.out.println("查询完成");
//            System.out.println("collectionCompletableFuture:"+ JSON.toJSONString(collectionCompletableFuture.get()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}