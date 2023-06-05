//package com.cqrcb.cloud.config;
//import org.apache.flink.api.common.JobID;
//import org.apache.flink.client.deployment.StandaloneClusterId;
//import org.apache.flink.client.program.PackagedProgram;
//import org.apache.flink.client.program.PackagedProgramUtils;
//import org.apache.flink.client.program.rest.RestClusterClient;
//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.configuration.JobManagerOptions;
//import org.apache.flink.configuration.RestOptions;
//import org.apache.flink.runtime.jobgraph.JobGraph;
//import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
//
//import java.io.File;
//import java.util.concurrent.CompletableFuture;
//
//
///**
// * @ClassName FlinkClient
// * @Description TODO
// * @Author Getech
// * @Date 2021/6/24 17:59
// */
//public class FlinkClient2 {
//
//    public static void main(String[] args) {
//        String jarFilePath = "D:\\02develop\\2020workspace\\apache-flink\\example\\WordCountSQL.jar";
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
//                    .setEntryPointClassName("org.apache.flink.table.examples.java.WordCountSQL")
//                    .setJarFile(jarFile)
//                    .setSavepointRestoreSettings(savepointRestoreSettings).build();
//            JobGraph jobGraph=PackagedProgramUtils.createJobGraph(program,configuration,parallelism,false);
//            CompletableFuture<JobID> result = client.submitJob(jobGraph);
//            JobID jobId=  result.get();
//            System.out.println("提交完成");
//            System.out.println("jobId:"+ jobId.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}