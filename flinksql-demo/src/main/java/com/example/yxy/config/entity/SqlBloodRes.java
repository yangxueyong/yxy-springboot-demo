package com.example.yxy.config.entity;

import lombok.Data;

import java.util.*;

@Data
public class SqlBloodRes {

    /**
     * 源表
     */
    private Set<MySourceTable> sourceTables = new HashSet<>();

    /**
     * sink表
     */
    private String sinkTable;

    @Data
    public static class MySourceTable{
        String tableName;
    }
}
