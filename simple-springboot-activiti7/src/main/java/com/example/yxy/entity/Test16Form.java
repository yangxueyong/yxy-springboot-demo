package com.example.yxy.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Test16Form {

    private String name;

    private String sex;

    private Date birthday;

    private BigDecimal money;

    private int fromNumber;
    private int toNumber;

}
