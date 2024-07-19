package com.example.yxy;

import com.example.yxy.entity.OrderGoods;
import com.example.yxy.util.PdfGeneratorTest;
import com.example.yxy.util.WaterMarkUtil;
import org.junit.jupiter.api.Test;
//import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
class PdfDemoApplicationTests {
    /**
     * 将html转换成pdf，并添加水印
     */
    @Test
    void html2pdf() throws Exception {
        // 循环添加
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            orderGoodsList.add(new OrderGoods("食品类","红蜻蜓" + i,"53.2",new BigDecimal(10),new BigDecimal(523)));
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("orderGoodsList", orderGoodsList);
        data.put("orgName", "测试机构号");
        data.put("sumMoney", "8991");
        data.put("oneJbrUserName", "张三");
        data.put("oneJbrUserPhone", "13900000000");


        //html路径
        String htmlPath = "/Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/simple-springboot-pdf/src/main/java/com/example/yxy/html";
        String htmlFileName = "test01.html";

        //生成得pdf路径
        String pdfPath = "/Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/simple-springboot-pdf/src/main/java/com/example/yxy/pdf";
        String fileName = "测试程序生成的.pdf";

        //html转pdf
        PdfGeneratorTest.generatePDF(data, htmlPath,
                htmlFileName,
                pdfPath,
                fileName);

        //pdf加水印
        WaterMarkUtil.addwaterMark(pdfPath + "/" + fileName, pdfPath + "/加水印之后.pdf", "测试水印");


        WaterMarkUtil.pdfSynPng(pdfPath + "/加水印之后.pdf",pdfPath + "/加水印之后-图片.pdf",pdfPath + "/water.png");
    }
}