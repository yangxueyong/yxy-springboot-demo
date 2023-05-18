package com.example.yxy;

import com.alibaba.excel.EasyExcel;
import com.example.yxy.excel.ImportDataListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;


@SpringBootTest
class DemoApplicationTests {


    public static void main(String[] args) {
        System.out.println("1231231231231");
        EasyExcel.read(new File("/Users/yxy/tmp/easyexcel_file/abc.xlsx"), Test.class, new ImportDataListener()).sheet().doRead();
    }

    @Test
    void saveDataByEasyExcel() {
        System.out.println("1231231231231");
        EasyExcel.read(new File("/Users/yxy/tmp/easyexcel_file/abc.xlsx"), Test.class, new ImportDataListener()).sheet().doRead();
    }

}