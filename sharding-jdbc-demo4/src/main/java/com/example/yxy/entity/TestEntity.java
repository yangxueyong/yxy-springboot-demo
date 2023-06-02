package com.example.yxy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TestEntity {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qutoTime;
}
