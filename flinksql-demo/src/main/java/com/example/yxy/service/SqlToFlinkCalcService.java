package com.example.yxy.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.yxy.config.CalciteUtil;
import com.example.yxy.config.entity.SqlBloodRes;
import com.example.yxy.entity.flink.FlinkSqlEntity;
import com.example.yxy.entity.flink.SqlToFlinkEntity;
import com.example.yxy.mapper.MyNoSplitTabMapper;
import com.example.yxy.mapper.RedAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.sql.parser.SqlParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class SqlToFlinkCalcService {
    @Autowired
    private RedAccountMapper redAccountMapper;
    @Autowired
    private MyNoSplitTabMapper myNoSplitTabMapper;

    public FlinkSqlEntity sqlToFlink(SqlToFlinkEntity sqlToFlink) {
        String syncSql = sqlToFlink.getSyncSql();
        SqlBloodRes sqlBloodRes = null;
        try {
            sqlBloodRes = CalciteUtil.getSqlBloodRes(syncSql);
        } catch (Exception e) {
            log.error("sql解析报错->",e);
            throw new RuntimeException(e);
        }
        String sinkTable = sqlBloodRes.getSinkTable();
        Set<SqlBloodRes.MySourceTable> sourceTables = sqlBloodRes.getSourceTables();

        return null;
    }



}
