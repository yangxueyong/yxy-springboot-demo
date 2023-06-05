package com.cqrcb.cloud.entity.flink;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlinkJobInfo implements Serializable {
    /**
     * batch or stream
     */
    private String jobType;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 并行度
     */
    private Integer parallelism;
    /**
     * flink init sql路径
     */
    private String flinkInitSqlPath;
    /**
     * flink exec sql路径
     */
    private String flinkExecSqlPath;
    /**
     * flink 重启时 恢复数据的savepoint地址
     */
    private String restartSavePointPath;
    /**
     * flink checkpoint地址
     */
    private String checkPointPath;
}
