package com.example.yxy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ContentRowHeight(30)
@HeadRowHeight(40)
@ColumnWidth(25)
public class StudentExportVo implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    @ExcelProperty(value = {"学生信息","学校"}, order = 1)
    @ContentLoopMerge(eachRow = 3)
    private String school;

    @ExcelProperty(value = {"学生信息","姓名"}, order = 2)
    private String name;

    @ExcelProperty(value = {"学生信息","性别"}, order = 3)
    private String sex;

    @ExcelProperty(value = {"学生信息","年龄"}, order = 4)
    private String age;

    @ExcelProperty(value = {"学生信息","城市"}, order = 5)
    private String city;

    @ExcelProperty(value = {"学生信息","备注"}, order = 6)
    private String remarks;

    }
