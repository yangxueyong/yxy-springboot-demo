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
import org.apache.flink.table.api.StatementSet;
import org.apache.flink.table.api.TableEnvironment;
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
public class FlinkTaskLocalSqlMain {
    static Logger logger = LoggerFactory.getLogger(FlinkTaskLocalSqlMain.class);

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        TableEnvironment tenv = StreamTableEnvironment.create(env);
        //该对象可以执行多个sql
        StatementSet statementSet = tenv.createStatementSet();
        //配置job名称
        Configuration configuration = tenv.getConfig().getConfiguration();
        configuration.setString("pipeline.name", "测试");

        tenv.executeSql("CREATE TABLE kafka_cust_red_money (\n" +
                "    cust_no String,\n" +
                "    trade_channel String,\n" +
                "    trade_money decimal(30,2),\n" +
                "    proctime as proctime()\n" +
                ") with(\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'test_kafka_cust_red_money',\n" +
                "  'properties.bootstrap.servers' = 'host.docker.internal:9092',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                "  'format' = 'json'\n" +
                ")");
        tenv.executeSql("CREATE TABLE t_p_cust_red_money (\n" +
                "    cust_no String,\n" +
                "    prod_no String,\n" +
                "    red_money decimal(30,2) ,\n" +
                "    create_time TIMESTAMP\n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://host.docker.internal:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_red_money',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")");
        tenv.executeSql("CREATE TABLE t_p_cust_main (\n" +
                "   cust_no String,\n" +
                "   cust_name String,\n" +
                "   phone String\n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://host.docker.internal:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_main',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")");


        statementSet.addInsertSql("insert into t_p_cust_red_money(cust_no,prod_no,red_money,create_time)\n" +
                " select k.cust_no,'Q00100101' as prod_no,trade_money * 0.2 as trade_money, localTimestamp as create_time\n" +
                " from kafka_cust_red_money k inner join t_p_cust_main for system_time as of k.proctime as m on k.cust_no = m.cust_no\n" +
                "     left join t_p_cust_red_money for system_time as of k.proctime as r on k.cust_no = r.cust_no\n" +
                "     where r.cust_no is null and k.trade_money>10 and k.trade_channel='wx'");

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
