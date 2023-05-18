package com.example.yxy.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.example.yxy.entity.Test;
import com.example.yxy.excel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    Set<String> ids = new CopyOnWriteArraySet<>();

    @RequestMapping("/saveDataByEasyexcel")
    private String saveDataByEasyexcel() throws Exception {

        ids = new CopyOnWriteArraySet<>();
        List<ImportDataParam.RowAndColData> rowAndColDataList = Arrays.asList(new ImportDataParam.RowAndColData(0, 0), new ImportDataParam.RowAndColData(0, 1));

        //目标文件
        File file = new File("/Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/easyexcel-demo/src/main/resources/abc.xlsx");
        //读取数据
        EasyExcel.read(file, Test.class, new ImportDataListener<Test>(new ImportDataParam(5, 5, rowAndColDataList,
                        //获取到指定单元格数据时，就调用
                        (rowIndex, colIndex, datas) -> getRowColData(rowIndex,colIndex,datas),
                        //获取元数据就调用
                        datas -> saveData(datas),
                        //所有获取执行完毕之后调用
                        () -> done())))
                .sheet()
                //表头的行数（就是需要跳过多少行）
                .headRowNumber(0).doRead();

        return ids.size() + "";
    }

    /**
     * 获取指定单元格的数据
     *
     * @param datas 数据
     */
    private void getRowColData(int rowIndex,int colIndex,List<Test> datas){
        //todo 找到需要的行列数据（也就是单元格数据）之后的执行逻辑
        log.info("rowIndex->{},colIndex->{},读取的制定行列数据->{}", rowIndex, colIndex, JSON.toJSONString(datas));
    }

    /**
     * 保存数据
     *
     * @param datas 数据
     */
    private void saveData(List<Test> datas){
        //todo 读取到正式数据之后的执行逻辑 一般是存入数据库
        log.info("读取的数据量->{},数据内容->{}", datas.size(), JSON.toJSONString(datas));
        for (Test data : datas) {
            String id = data.getId();
            ids.add(id);
        }
    }


    /**
     * 完成
     */
    private void done(){
        //todo 数据读取完成之后的执行逻辑
        log.info("已完结");
    }
}
