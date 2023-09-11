package com.example.yxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "my_user")
public class MyUser {
    @TableId(value = "id",type = IdType.AUTO)
    private BigDecimal id;

    @TableField("createTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",lenient = OptBoolean.TRUE)
    private Date createTime;

    @TableField("name")
    private String name;

    @TableField("phone")
    private String phone;

    @TableField("gender")
    private String gender;

    @TableField("address")
    private String address;
}
