package com.example.yxy;

import com.example.yxy.entity.flink.FlinkJobInfo;
import com.example.yxy.function.GetDateIntByTypeFun;
import com.example.yxy.function.GetMaxMinFun;
import com.example.yxy.function.GetMoneyForRewardFun;
import com.example.yxy.function.SplitFunction;
import com.example.yxy.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

/**
 * flink任务入口方法
 *
 * @author yxy
 * @date 2023/06/05
 */
public class FlinkTaskMain {
    static Logger logger = LoggerFactory.getLogger(FlinkTaskMain.class);

    public static void main(String[] args) {
        if(args == null || args.length == 0){
            logger.error("参数为空-->");
            return;
        }

        String paramFile = args[0];
        if (paramFile == null || "".equals(paramFile)) {
            logger.error("参数1为空-->");
            return;
        }
        logger.info("传入的参数->{}",paramFile);
        FlinkJobInfo file2FlinkjobInfo = getFile2FlinkJobInfo(paramFile);
        if (file2FlinkjobInfo == null) {
            logger.error("转换之后的对象为空-->");
            return;
        }
        String taskName = file2FlinkjobInfo.getJobName();
        if (taskName == null || "".equals(taskName)) {
            logger.error("任务名称不可为空-->");
            return;
        }
        String checkPointPath = file2FlinkjobInfo.getCheckPointPath();
        String restartSavePointPath = file2FlinkjobInfo.getRestartSavePointPath();
        String taskType = file2FlinkjobInfo.getJobType();
        StreamExecutionEnvironment env = null;
        if(!StringUtils.isEmpty(restartSavePointPath)){
            System.out.println("进入savepoint--》》》》》》》》》》》》》》》》》》");
            Configuration conf = new Configuration();
            conf.setString("execution.savepoint.path", restartSavePointPath);
            env = StreamExecutionEnvironment.getExecutionEnvironment(conf);
        }else {
            env = StreamExecutionEnvironment.getExecutionEnvironment();
        }
        TableEnvironment tenv = StreamTableEnvironment.create(env);
        //设置状态清理参数
        if(taskType != null && !"".equals(taskType)){
            if ("flink-stream".equals(taskType) && !StringUtils.isEmpty(checkPointPath)) {
                System.out.println("+++++++++++++++++++flink-stream+++++++++++++++++++");
                // 传入两个最基本ck参数：间隔时长，ck模式
                env.enableCheckpointing(2000, CheckpointingMode.EXACTLY_ONCE);
                CheckpointConfig checkpointConfig = env.getCheckpointConfig();
                checkpointConfig.setCheckpointStorage(checkPointPath);
                // 设置ck对齐的超时时长
                checkpointConfig.setAlignedCheckpointTimeout(Duration.ofMinutes(10000));
                // 设置ck算法模式
                checkpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
                // ck的间隔时长
                checkpointConfig.setCheckpointInterval(2000);
                // 用于非对齐算法模式下，在job恢复时让各个算子自动抛弃掉ck-5中飞行数据
                //checkpointConfig.setCheckpointIdOfIgnoredInFlightData(5);
                // job cancel调时，保留最后一次ck数据
                checkpointConfig.setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
                // 是否强制使用  非对齐的checkpoint模式
                checkpointConfig.setForceUnalignedCheckpoints(false);
                // 允许在系统中同时存在的飞行中（未完成的）的ck数
                checkpointConfig.setMaxConcurrentCheckpoints(5);
                //  设置两次ck之间的最小时间间隔，用于防止checkpoint过多地占用算子的处理时间
                checkpointConfig.setMinPauseBetweenCheckpoints(2000);
                // 一个算子在一次checkpoint执行过程中的总耗费时长超时上限
                checkpointConfig.setCheckpointTimeout(3000);
                // 允许的checkpoint失败最大次数
                checkpointConfig.setTolerableCheckpointFailureNumber(10);
            }else{
                System.out.println("+++++++++++++++++++flink-batch+++++++++++++++++++");
            }
        }
        //该对象可以执行多个sql
        StatementSet statementSet = tenv.createStatementSet();
        //设置并行度
        Integer parallelism = file2FlinkjobInfo.getParallelism();
        if(parallelism == null || parallelism <= 0){
            parallelism = Runtime.getRuntime().availableProcessors();
        }
        env.setParallelism(parallelism);
        //配置job名称
        Configuration configuration = tenv.getConfig().getConfiguration();
        configuration.setString("pipeline.name", taskName);

        String fileInitSqlContent = getFileContent2(file2FlinkjobInfo.getFlinkInitSqlPath());
        String[] fileInitSqls = fileInitSqlContent.split(";");
        for (String fileInitSql : fileInitSqls) {
            logger.info("initSql1->{}", fileInitSql);
            tenv.executeSql(fileInitSql);
        }
        //创建函数
        tenv.createTemporarySystemFunction("getMaxMinFun", new GetMaxMinFun());
        tenv.createTemporarySystemFunction("getDateByTypeFun", new GetDateIntByTypeFun());
        tenv.createTemporarySystemFunction("getMoneyForRewardFun", new GetMoneyForRewardFun());
        tenv.createTemporarySystemFunction("splitFunction", new SplitFunction());

        String fileExecContent = getFileContent2(file2FlinkjobInfo.getFlinkExecSqlPath());
        logger.info("execSql1->{}", fileExecContent);
        String[] split = fileExecContent.split(";");

        for (String s : split) {
            statementSet.addInsertSql(s);
        }
        statementSet.execute();
    }


    /**
     * 将rule文件中的内容转换成对象
     *
     * @param filePath 文件路径
     * @return {@link FlinkJobInfo}
     */
    public static FlinkJobInfo getFile2FlinkJobInfo(String filePath) {
        return FileUtil.getFileToJavaObj(filePath, FlinkJobInfo.class);
    }

    public static String getFileContent(String filePath) {
        return FileUtil.getFileToJSONStr(filePath);
    }

    /**
     * 获取文件内容 内容为sql信息
     *
     * @param filePath 文件路径
     * @return {@link String}
     */
    public static String getFileContent2(String filePath) {
        File file = new File(filePath);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
