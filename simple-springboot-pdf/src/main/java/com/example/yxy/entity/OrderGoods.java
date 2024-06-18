package com.example.yxy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoods {
    private String goodsType;
    private String goodsBrand;
    private String goodsPrice;
    private BigDecimal goodsNum;
    private BigDecimal sumMoney;
}
