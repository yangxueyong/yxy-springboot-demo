package com.cqrcb.cloud.function;

import lombok.Data;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.table.functions.ScalarFunction;

import java.math.BigDecimal;

public class GetMaxMinFun extends ScalarFunction {
    BigDecimal z100 = BigDecimal.valueOf(100);

    public String eval(String value, String max, String min) {
//        String s ="{\"a\":123}";
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            objectMapper.readValue(s,User.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        BigDecimal value1 = BigDecimal.valueOf(Double.valueOf(value)).divide(z100, 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal max1 = BigDecimal.valueOf(Double.valueOf(max));
        BigDecimal min1 = BigDecimal.valueOf(Double.valueOf(min));
        //如果value比max大
        if (value1.compareTo(max1) == 1) {
            return String.valueOf(max);
        }
        //如果value比min小
        if (value1.compareTo(min1) == -1) {
            return String.valueOf(min);
        }
        return String.valueOf(value1);
    }
    @Data
    static class User{
        private String a;
    }
}
    