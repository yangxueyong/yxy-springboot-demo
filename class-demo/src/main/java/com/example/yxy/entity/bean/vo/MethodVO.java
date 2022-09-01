package com.example.yxy.entity.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodVO {

    /**
     * clsBean
     */
    private Object clsBean;

    /**
     * method
     */
    private Method method;

}