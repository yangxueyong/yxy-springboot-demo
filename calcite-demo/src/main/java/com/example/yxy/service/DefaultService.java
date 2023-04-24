package com.example.yxy.service;
 

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Service
public class DefaultService {
    public static final Properties CONFIG = new Properties();
    private Connection csvConn;
    private Connection mysqlConn;

    private Connection mcsqlConn;


    @PostConstruct
    public void init() throws SQLException {
        Properties config = new Properties();
        config.put("model", DefaultService.class.getClassLoader().getResource("csv_default.json").getPath());
        config.put("caseSensitive", "false");
        csvConn = DriverManager.getConnection("jdbc:calcite:",config);

        config = new Properties();
        config.put("model", DefaultService.class.getClassLoader().getResource("mysql_default.json").getPath());
        config.put("caseSensitive", "false");
        mysqlConn = DriverManager.getConnection("jdbc:calcite:",config);

        config = new Properties();
        config.put("model", DefaultService.class.getClassLoader().getResource("mc_default.json").getPath());
        config.put("caseSensitive", "false");
        mcsqlConn = DriverManager.getConnection("jdbc:calcite:",config);
    }

    public void queryByMoreDataMc() throws Exception {
        List<String> sqlList = new ArrayList<>();
//        sqlList.add("select count(t.id) from csv.csv_moredata_user t where t.id < 10000");
//        sqlList.add("insert into mysql.aa select * from csv.csv_moredata1w_user");
        sqlList.add("select count(t.id) from csv.csv_moredata1w_user t left join mysql.detail3 t2 on t.id = t2.id where t2.id is null");
        for (String sql : sqlList) {
            System.out.println("-----------------");
            System.out.println(sql);
            long t1 = System.currentTimeMillis();
            Statement statement = mcsqlConn.createStatement();
            try {
                printResultSet(statement.executeQuery(sql));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                statement.close();
            }
            long t2 = System.currentTimeMillis();
            System.out.println("耗时->" + (t2 - t1));
        }
    }

    public void queryByMc() throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select t.id,t.name,t2.age from csv.csv_user t left join mysql.detail t2 on t.id = t2.id");
        for (String sql : sqlList) {
            System.out.println("-----------------");
            System.out.println(sql);
            long t1 = System.currentTimeMillis();
            Statement statement = mcsqlConn.createStatement();
            try {
                printResultSet(statement.executeQuery(sql));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                statement.close();
            }
            long t2 = System.currentTimeMillis();
            System.out.println("耗时->" + (t2 - t1));
        }
    }

    public void queryByMysql() throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * from mysql.tab1");
        for (String sql : sqlList) {
            System.out.println("-----------------");
            System.out.println(sql);
            Statement statement = mysqlConn.createStatement();
            try {
                printResultSet(statement.executeQuery(sql));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                statement.close();
            }
        }
    }




    public void queryByWhite() throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * from csv.csv_white where 客户号=1");
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