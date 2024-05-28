package com.example.yxy.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TestJsonEntity {

//    @TableField("id")
    private long idd;

    private String name;

    private String address;

    private Date birDate;

    private BigDecimal mon;
}
