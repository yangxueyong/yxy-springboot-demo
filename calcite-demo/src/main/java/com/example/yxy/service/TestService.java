package com.example.yxy.service;
 

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Service
public class TestService {
    public static final Properties CONFIG = new Properties();
    private Connection csvConn;

    private Connection mcsqlConn;


    @PostConstruct
    public void init() throws SQLException {
        Properties config = new Properties();
        config.put("model", TestService.class.getClassLoader().getResource("csv_default.json").getPath());
        config.put("caseSensitive", "false");
        csvConn = DriverManager.getConnection("jdbc:calcite:",config);

        config = new Properties();
        config.put("model", DefaultService.class.getClassLoader().getResource("mc_default.json").getPath());
        config.put("caseSensitive", "false");
        mcsqlConn = DriverManager.getConnection("jdbc:calcite:",config);
    }



//    public void queryByMysql() throws Exception {
//        List<String> sqlList = new ArrayList<>();
//        sqlList.add("select * from csv.csv_white where 客户号 is null or 客户姓名 is null or 客户号 = '' ");
//        for (String sql : sqlList) {
//            System.out.println("-----------------");
//            System.out.println(sql);
//            printResultSet(csvConn.createStatement().executeQuery(sql));
//        }
//    }


//    user3

    public void insertWhite() throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("insert into mysql.user3(id,name) values('a','zhangsan')");
//        sqlList.add("insert into mysql.user3(id,name) select 客户号,客户姓名 from csv.csv_white c where c.客户号 is not null and c.客户号 <> '' ");
//        sqlList.add("select * from csv.csv_white where 客户号 is null or 客户姓名 is null or 客户号 = '' ");
        for (String sql : sqlList) {
            System.out.println("-----------------");
            System.out.println(sql);
            Statement statement = mcsqlConn.createStatement();
            try {
                int i = statement.executeUpdate(sql);
                System.out.println("i->" + i);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                statement.close();
            }
        }
    }

    public void queryByWhite() throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * from csv.csv_white where 客户号 is null or 客户姓名 is null or 客户号 = '' ");
        for (String sql : sqlList) {
            System.out.println("-----------------");
            System.out.println(sql);
            Statement statement = csvConn.createStatement();
            try {
                printResultSet(statement.executeQuery(sql));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                statement.close();
            }
        }
    }



 
    public void queryByCsv() throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * from csv.csv_user");
        sqlList.add("select id, name || '_after_append' from csv_user");
        sqlList.add("select t.id,t.name,t2.age from csv_user t left join csv_detail t2 on t.id = t2.id");
        for (String sql : sqlList) {
            System.out.println("-----------------");
            System.out.println(sql);
            Statement statement = csvConn.createStatement();
            try {
                printResultSet(statement.executeQuery(sql));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                statement.close();
            }
        }
    }
 
    private void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while(resultSet.next()){
            List<Object> row = new ArrayList<>();
            for (int i = 1; i < columnCount+1; i++) {
                row.add(resultSet.getObject(i));
            }
            System.out.println(row);
        }
    }
}