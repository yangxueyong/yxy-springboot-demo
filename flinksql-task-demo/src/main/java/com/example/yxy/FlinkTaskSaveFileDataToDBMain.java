package com.example.yxy;

import com.example.yxy.entity.flink.FlinkJobInfo;
import com.example.yxy.util.FileUtil;
import org.apache.flink.configuration.Configuration;
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

/**
 * flink任务入口方法
 *
 * @author yxy
 * @date 2023/06/05
 */
public class FlinkTaskSaveFileDataToDBMain {
    static Logger logger = LoggerFactory.getLogger(FlinkTaskSaveFileDataToDBMain.class);

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        TableEnvironment tenv = StreamTableEnvironment.create(env);
        //该对象可以执行多个sql
        StatementSet statementSet = tenv.createStatementSet();
        //配置job名称
        Configuration configuration = tenv.getConfig().getConfiguration();
        configuration.setString("pipeline.name", "测试");
//        String initsSQLfile = args[0]
        tenv.executeSql("CREATE TABLE flink_file ( \n" +
                "  id int, \n" +
                "  name STRING, \n" +
                "  age int\n" +
                ") WITH ( \n" +
                "  'connector'='filesystem', \n" +
                "  'path'='hdfs://localhost:9000/flink_test_file/abc.txt', \n" +
                "  'format'='csv' \n" +
                ")");
        tenv.executeSql("CREATE TABLE flink_file_data ( \n" +
                "  id int, \n" +
                "  name STRING, \n" +
                "  age int\n" +
                ") WITH ( \n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://127.0.0.1:3306/test?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 'flink_file_data',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")");


        statementSet.addInsertSql("insert into flink_file_data(id,name,age)\n" +
                " select * from flink_file");

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
