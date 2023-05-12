package com.example.yxy.entity.flink;

import lombok.Data;

@Data
public class SqlToFlinkEntity {
    /**
     * 前置sql
     */
    private String preSql;

    /**
     * 同步sql
     */
    private String syncSql;

    /**
     * 验证sql
     */
    private String verifySql;
    /**
     * 并行度
     */
    private int parallelism;

    @Data
    public static class TableData{
        /**
         * 表名
         */
        private String tableName;
        /**
         * 主键
         */
        private String primaryKey;
        /**
         * 分区列
         */
        private String scanColumn;
        /**
         * 分区数量
         */
        private String scanNum;
        /**
         * 分区最小值
         */
        private String scanMin;
        /**
         * 分区最大值
         */
        private String scanMax;
    }

}
