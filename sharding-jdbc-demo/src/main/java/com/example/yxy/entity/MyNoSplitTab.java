package com.example.yxy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@TableName(value = "my_no_split_tab")
public class MyNoSplitTab {
 
    private String id;
 
    private String name;
 
    private String address;
 
    private String phone;
 
    private String remark;
}