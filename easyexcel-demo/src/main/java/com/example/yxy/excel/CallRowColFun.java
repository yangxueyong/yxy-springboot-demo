package com.example.yxy.excel;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@FunctionalInterface
public interface CallRowColFun {
    void callBack(int rowIndex,int colIndex,List datas); // 只能包含一个抽象方法
}