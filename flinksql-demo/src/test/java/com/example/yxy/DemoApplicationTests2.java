package com.example.yxy;

import com.example.yxy.util.FlinkJobClientUtil;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests2 {


    /**
     * @link https://nightlies.apache.org/flink/flink-docs-release-1.15/zh/docs/connectors/table/jdbc/
     *
     *
     * 假设白名单100万条数据
     *
     */
    @Test
    void batchMetaTableDataSaveToCustProdDB() {
        //得到一个执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //并行度
        env.setParallelism(1);
        //创建一个流表的执行环境
        StreamTableEnvironment tenv = StreamTableEnvironment.create(env);

        //假设白名单100万条数据
        //每20万提交一次任务
        //因此需要提交5次
        for (int i = 0; i < 5; i++) {
            //每一次任务
            String sql2 = "CREATE TABLE t_p_cust_white_list (\n" +
                    "\tcust_no String,\n" +
                    "\tact_no String\n" +
                    ") with(\n" +
                    "   'connector' = 'jdbc',\n" +
                    "   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',\n" +
                    "   'table-name' = 't_p_cust_white_list',\n" +
                    "   'username'='root',\n" +
                    "   'password'='123456789'" +
                    "   ,'scan.partition.column'='auto_id'" +
                    "   ,'scan.partition.num'='5'" +
                    "   ,'scan.partition.lower-bound'='"+ (i - 1) * 200000 +"'" +
                    "   ,'scan.partition.upper-bound'='"+ (i) * 200000 +"'\n" +
                    ")";
            tenv.executeSql(sql2);

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
                    "   ,'scan.partition.column'='auto_id'" +
                    "   ,'scan.partition.num'='5'" +
                    "   ,'scan.custom.query'='select * from (select * from t_p_cust_deposit_prod m inner join t_p_cust_white_list l " +
                    "   on m.cust_no = l.cust_no and l.auto_id between ? and ?)'" +
                    "   ,'scan.partition.lower-bound'='"+ (i - 1) * 200000 +"'" +
                    "   ,'scan.partition.upper-bound'='"+ (i) * 200000 +"'\n" +
                    ")";
            tenv.executeSql(sql1);

            //在flink中创建一个名字为t_p_cust_prod的表，它映射到数据库test_flink中的t_p_cust_prod表
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

            String sql4 = "insert into t_p_fin_detail(cust_no,act_no,conf_no,score)\n" +
                    "select cust_no," +
                    "'xx1' as act_no," +
                    "my_split(concat(" +
                    "'con1',case when p.main_prod_no like 'A%' then 1 else 0 end," +
                    "'con2',case when p.main_prod_no like 'B%' then 2 else 0 end" +
                    ")) as scores \n" +
                    "from t_p_cust_white_list l left join t_p_cust_deposit_prod p on l.cust_no=p.cust_no " +
                    "where p.cust_no is not null";
            tenv.executeSql(sql4).print();
        }
    }


}