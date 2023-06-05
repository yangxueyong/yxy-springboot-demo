package com.example.yxy.entity.flink.io;

import lombok.Data;


@Data
public class FlinkTaskSqlInfoIO {
    /**
     * batch or stream
     */
    private String taskType;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 日期(yyyyMMdd) 用于做路径
     */
    private String date;
    /**
     * 文件路径 相当于二级目录
     */
    private String pathName;
    /**
     * 并行度
     */
    private Integer parallelism;
    /**
     * flink init sql路径
     */
    private String flinkInitSql;

    private String flinkInitFileName;
    /**
     * flink exec sql路径
     */
    private String flinkExecSql;

    private String flinkExecFileName;
}
