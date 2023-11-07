package com.example.yxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TestAutoIdEntity {

//    @TableField("id")
    private long idd;

    private String name;

    private String address;
}
