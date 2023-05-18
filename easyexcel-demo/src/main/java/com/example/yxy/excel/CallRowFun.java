package com.example.yxy.excel;

import java.util.List;

@FunctionalInterface
public interface CallRowFun {
    void callBack(List datas); // 只能包含一个抽象方法
}