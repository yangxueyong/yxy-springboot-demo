package com.example.yxy.entity.flink;

import lombok.Data;

@Data
public class FlinkSqlEntity {
    /**
     * 初始化sql
     */
    private String initSql;

    /**
     * 运行sql
     */
    private String execSql;

}
