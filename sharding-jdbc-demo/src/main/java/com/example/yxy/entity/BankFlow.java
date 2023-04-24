package com.example.yxy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@TableName(value = "money_flow")
public class BankFlow {
 
    private Long id;
 
    private String flowId;
 
    private BigDecimal money;
 
    private Instant flowTime;
 
    private Instant createTime;
 
    private Long shardingTime;
}