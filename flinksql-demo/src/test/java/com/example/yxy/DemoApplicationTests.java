package com.example.yxy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.yxy.util.FlinkJobClientUtil;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DemoApplicationTests {


    /**
     * @link https://nightlies.apache.org/flink/flink-docs-release-1.15/zh/docs/connectors/table/jdbc/
     *
     * 元数据为表
     * 将结果数据保存到表
     *
     */
    @Test
    void batchMetaTableDataSaveToCustProdDB() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tenv = StreamTableEnvironment.create(env);

        /**
         *
         * 1，flink的表名可以与数据库中的表名不一样，但建议保持一致
         * 2，flink的表字段要与数据库中的字段保持一致
         * 3，flink表的字段类型和数据库中的字段类型不是完全一样
         *
         */
        String sql1 = "CREATE TABLE t_p_cust_deposit_prod (\n" +
                "\tcust_no String,\n" +
                "\tmain_prod_no String,\n" +
                "\tsub_prod_no String,\n" +
                "\tamount decimal(30,2) ,\n" +
                "\tbegin_time TIMESTAMP ,\n" +
                "\tend_time TIMESTAMP \n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_deposit_prod',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'" +
//                "   ,'scan.partition.column'='auto_id'" +
//                "   ,'scan.partition.num'='2'" +
//                "   ,'scan.partition.lower-bound'='1'" +
//                "   ,'scan.partition.upper-bound'='4'\n" +
                ")";
        tenv.executeSql(sql1);


        String sql2 = "CREATE TABLE t_p_cust_loan_prod (\n" +
                "\tcust_no String,\n" +
                "\tmain_prod_no String,\n" +
                "\tsub_prod_no String,\n" +
                "\tamount decimal(30,2) ,\n" +
                "\tbegin_time TIMESTAMP ,\n" +
                "\tend_time TIMESTAMP \n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_loan_prod',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")";
        tenv.executeSql(sql2);

        String sql3 = "CREATE TABLE t_p_cust_prod (\n" +
                "\tcust_no String,\n" +
                "\tmain_prod_no String,\n" +
                "\tnum decimal(30,2),\n" +
                "\tamount decimal(30,2) ," +
                "primary key(cust_no,main_prod_no) not enforced \n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_prod',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")";
        tenv.executeSql(sql3);

        String sql4 = "insert into t_p_cust_prod(cust_no,main_prod_no,num,amount)\n" +
                "select cust_no,main_prod_no,count(*) num, sum(amount) as amount \n" +
                "from t_p_cust_deposit_prod group by cust_no,main_prod_no";
        tenv.executeSql(sql4).print();
    }



    /**
     * @link https://nightlies.apache.org/flink/flink-docs-release-1.15/zh/docs/connectors/table/filesystem/
     *
     * 元数据为文件
     * 将结果数据保存到表
     *
     */
    @Test
    void batchMetaFileSaveToCustProdDB() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tenv = StreamTableEnvironment.create(env);


        String sql1 = "CREATE TABLE t_p_cust_deposit_prod (\n" +
                "\tcust_no String,\n" +
                "\tmain_prod_no String,\n" +
                "\tsub_prod_no String,\n" +
                "\tamount decimal(30,2) ,\n" +
                "\tbegin_time TIMESTAMP ,\n" +
                "\tend_time TIMESTAMP \n" +
                ") with(\n" +
                "  'connector'='filesystem',\n" +
                "  'path'='file:////Users/yxy/tmp/data/t_p_cust_deposit_prod',\n" +
                "  'format'='csv',\n" +
                "  'csv.quote-character'='\"', " +
                "  'csv.ignore-parse-errors'='true' " +
                ")";
        tenv.executeSql(sql1);


        String sql2 = "CREATE TABLE t_p_cust_loan_prod (\n" +
                "\tcust_no String,\n" +
                "\tmain_prod_no String,\n" +
                "\tsub_prod_no String,\n" +
                "\tamount decimal(30,2) ,\n" +
                "\tbegin_time TIMESTAMP ,\n" +
                "\tend_time TIMESTAMP \n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_loan_prod',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")";
        tenv.executeSql(sql2);

        String sql3 = "CREATE TABLE t_p_cust_prod (\n" +
                "\tcust_no String,\n" +
                "\tmain_prod_no String,\n" +
                "\tnum decimal(30,2),\n" +
                "\tamount decimal(30,2) ," +
                "primary key(cust_no,main_prod_no) not enforced \n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_prod',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")";
        tenv.executeSql(sql3);

        String sql4 = "insert into t_p_cust_prod(cust_no,main_prod_no,num,amount)\n" +
                "select cust_no,main_prod_no,count(*) num, sum(amount) as amount \n" +
                "from t_p_cust_deposit_prod group by cust_no,main_prod_no";
        tenv.executeSql(sql4).print();
    }


    /**
     * @link https://nightlies.apache.org/flink/flink-docs-release-1.15/zh/docs/connectors/table/kafka/
     *
     * 实时处理，元数据为kafka
     * {"cust_no":"zhangsan","trade_money":12,"trade_channel":"wx","trade_time":"2023-01-01 10:10:01"}
     *
     * 订阅kafka消息，给客户派红包(将结果数据保存到表)，红包为交易金额的20%
     * 条件1：交易金额必须大于10元，且交易渠道必须为微信支付
     * 条件2：客户必须在系统中存在
     * 条件3：给客户派发过之后不能再派发
     *
     * kafka 队列 -> k
     *
     * 结果表 jg
     * 客户主表 kz
     *
     *
     */
    @Test
    void streamMetaKafkaSaveToCustProdDB() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tenv = StreamTableEnvironment.create(env);


        String sql1 = "CREATE TABLE kafka_cust_red_money (\n" +
                "\tcust_no String,\n" +
                "\ttrade_channel String,\n" +
                "\ttrade_money decimal(30,2)," +
                "proctime as proctime()" +
                ") with(\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'test_kafka_cust_red_money',\n" +
                "  'properties.bootstrap.servers' = 'localhost:9092',\n" +
//                "  'properties.group.id' = 'testGroup',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                "  'format' = 'json' " +
                ")";
        tenv.executeSql(sql1);


        String sql3 = "CREATE TABLE t_p_cust_main (\n" +
                "\tcust_no String,\n" +
                "\tcust_name String,\n" +
                "\tphone String\n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_main',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")";
        tenv.executeSql(sql3);

        String sql2 = "CREATE TABLE t_p_cust_red_money (\n" +
                "\tcust_no String,\n" +
                "\tprod_no String,\n" +
                "\tred_money decimal(30,2) ,\n" +
                "\tcreate_time TIMESTAMP \n" +
                ") with(\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                "   'table-name' = 't_p_cust_red_money',\n" +
                "   'username'='root',\n" +
                "   'password'='123456789'\n" +
                ")";
        tenv.executeSql(sql2);


        String sql4 = "insert into t_p_cust_red_money(cust_no,prod_no,red_money,create_time)\n" +
                " select k.cust_no,'Q00100101' as prod_no,trade_money * 0.2 as trade_money, localTimestamp as create_time \n" +
                " from kafka_cust_red_money k inner join t_p_cust_main for system_time as of k.proctime as m on k.cust_no = m.cust_no " +
                " left join t_p_cust_red_money for system_time as of k.proctime as r on k.cust_no = r.cust_no " +
                " where r.cust_no is null and k.trade_money>10 and k.trade_channel='wx'";

        System.out.println(sql1);
        System.out.println(sql2);
        System.out.println(sql3);
        System.out.println(sql4);

        tenv.executeSql(sql4).print();



    }


    /**
     * 元数据为kafka ，开窗口
     * 将结果数据保存到表
     */
    @Test
    void batchMetaKafkaWindowSaveToCustProdDB() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tenv = StreamTableEnvironment.create(env);


        String sql1 = "CREATE TABLE kafka_cust_red_money (\n" +
                "\tcust_no String,\n" +
                "\ttrade_channel String,\n" +
                "\ttrade_money decimal(30,2)," +
                "\ttrade_time string," +
                " proctime as proctime()," +
                " rt as TO_TIMESTAMP(trade_time), " +
                " watermark for rt  as rt - interval '1' second" +
                ") with(\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'test_kafka_cust_red_money',\n" +
                "  'properties.bootstrap.servers' = 'localhost:9092',\n" +
//                "  'properties.group.id' = 'testGroup',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                "  'format' = 'json' " +
                ")";
        tenv.executeSql(sql1);


        // 每分钟，计算最近5分钟的交易总额
//        String sql4 = "select\n" +
//                "  window_start,\n" +
//                "  window_end,\n" +
//                "  sum(trade_money) as sum_money\n" +
//                "from table(\n" +
//                " hop(table kafka_cust_red_money,descriptor(rt), interval '1' minutes, interval '5' minutes)\n" +
//                ")\n" +
//                "group by window_start,window_end";

        // 每5秒，计算最近15秒的交易总额
        String sql4 = "select\n" +
                "  window_start,\n" +
                "  window_end,\n" +
                "  sum(trade_money) as sum_money\n" +
                "from table(\n" +
                " hop(table kafka_cust_red_money,descriptor(rt), interval '5' seconds, interval '15' seconds)\n" +
                ")\n" +
                "group by window_start,window_end";
        tenv.executeSql(sql4).print();
    }





    /**
     * 通过命令行的方式提交任务
     */
    private void submitTaskByCmd(){
        /**
         * bin/sql-client.sh  -i /Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/flinksql-demo/src/main/resources/real_flink_init.sql -f /Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/flinksql-demo/src/main/resources/real_flink_exec.sql
         */
    }







    @Autowired
    private FlinkJobClientUtil flinkJobClientUtil;

    /**
     * 使用jar包提交任务
     * 将结果数据保存到表
     */
    @Test
    void batchMetaTableDataSaveToCustProdDBByWeb() throws Exception {
//        flinkJobClientUtil.submitStreamFlinkJob("/Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/flinksql-demo/src/main/resources/batch_flink_rule.txt");
        flinkJobClientUtil.submitStreamFlinkJob("/Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/flinksql-demo/src/main/resources/real_flink_rule.txt");
    }
}