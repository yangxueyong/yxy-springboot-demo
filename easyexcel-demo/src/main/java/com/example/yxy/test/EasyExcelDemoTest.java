package com.example.yxy.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.example.yxy.entity.ComplexHeadData;
import com.example.yxy.entity.DemoData;
import com.example.yxy.entity.DemoMergeData;
import com.example.yxy.entity.StudentExportVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EasyExcelDemoTest {
    public static void main(String[] args) {
//        List<StudentExportVo> list = getStudentExportVos();
//        EasyExcel.write("test.xlsx", StudentExportVo.class).sheet("学生信息").doWrite(list);
        String fileName = null;
//        // 方法1 注解
//        fileName =  "mergeWrite" + System.currentTimeMillis() + ".xlsx";
//        // 在DemoStyleData里面加上ContentLoopMerge注解
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        EasyExcel.write(fileName, DemoMergeData.class).sheet("模板").doWrite(data());

//        // 方法2 自定义合并单元格策略
//        fileName = "mergeWrite" + System.currentTimeMillis() + ".xlsx";
//        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
////        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 1,2);
//
//        OnceAbsoluteMergeStrategy mergeStrategy = new OnceAbsoluteMergeStrategy(1, 3, 0,0);
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        EasyExcel.write(fileName, DemoData.class)
//                .needHead(false)
//                .registerWriteHandler(mergeStrategy).sheet("模板").doWrite(data());

        complexHeadWrite();

    }

    /**
     * 复杂头写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定复杂的头
     * <p>
     * 3. 直接写即可
     */
    public static void complexHeadWrite() {
        String fileName = "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(data());
    }

    private static List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 11; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    //数据制造
    public static List<StudentExportVo> getStudentExportVos() {
        List<StudentExportVo> list = new ArrayList<>();
        StudentExportVo v1 = new StudentExportVo();
        v1.setSchool("北京大学");
        v1.setName("张三");
        v1.setSex("男");
        v1.setAge("20");
        v1.setCity("北京");
        v1.setRemarks("无");
        list.add(v1);
        StudentExportVo v2 = new StudentExportVo();
        v2.setSchool("北京大学");
        v2.setName("李四");
        v2.setSex("男");
        v2.setAge("22");
        v2.setCity("上海");
        v2.setRemarks("无");
        list.add(v2);
        StudentExportVo v3 = new StudentExportVo();
        v3.setSchool("北京大学");
        v3.setName("王五");
        v3.setSex("女");
        v3.setAge("22");
        v3.setCity("青岛");
        v3.setRemarks("无");
        list.add(v3);
        StudentExportVo v4 = new StudentExportVo();
        v4.setSchool("清华大学");
        v4.setName("赵六");
        v4.setSex("女");
        v4.setAge("21");
        v4.setCity("重庆");
        v4.setRemarks("无");
        list.add(v4);
        StudentExportVo v5 = new StudentExportVo();
        v5.setSchool("武汉大学");
        v5.setName("王强");
        v5.setSex("男");
        v5.setAge("24");
        v5.setCity("长沙");
        v5.setRemarks("无");
        list.add(v5);
        StudentExportVo v6 = new StudentExportVo();
        v6.setSchool("武汉大学");
        v6.setName("赵燕");
        v6.setSex("女");
        v6.setAge("21");
        v6.setCity("深圳");
        v6.setRemarks("无");
        list.add(v6);
        StudentExportVo v7 = new StudentExportVo();
        v7.setSchool("厦门大学");
        v7.setName("陆仟");
        v7.setSex("女");
        v7.setAge("21");
        v7.setCity("广州");
        v7.setRemarks("无");
        list.add(v7);
        return list;
    }

}
