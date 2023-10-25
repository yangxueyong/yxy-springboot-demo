package com.example.yxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("test_auto_id")
public class TestAutoIdEntity2 {

//    @TableField("id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "jdbc")
    @TableId(value="id",type = IdType.AUTO )
    private BigDecimal idd;

    private String name;

    private String address;
}
