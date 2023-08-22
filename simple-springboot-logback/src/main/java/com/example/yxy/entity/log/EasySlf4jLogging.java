package com.example.yxy.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "custom_log")
public class EasySlf4jLogging {
    @TableId(value = "id",type = IdType.AUTO)
    private BigDecimal id;

    @TableField("logTime")
    private Date logTime;

    @TableField("logThread")
    private String logThread;

    @TableField("logClass")
    private String logClass;

    @TableField("logMethod")
    private String logMethod;

    @TableField("logLineNum")
    private long logLineNum;

    @TableField("logLevel")
    private String logLevel;

    @TableField("trackId")
    private String trackId;

    @TableField("logContent")
    private String logContent;
}
