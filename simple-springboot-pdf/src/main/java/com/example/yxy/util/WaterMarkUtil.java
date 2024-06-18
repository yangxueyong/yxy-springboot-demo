package com.example.yxy.util;

import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 加水印
 */
public class WaterMarkUtil {

    private //定义字体
    static BaseFont base = null;

    static {
        try {
            //字体路径
            String baseFont = "/Users/yxy/work/java/demo/yxy-springboot-demo/yxy-springboot-demo/simple-springboot-pdf/src/main/java/com/example/yxy/ttf/youshebiaotihei.ttf";
            base = BaseFont.createFont(baseFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //需要添加水印的文件
        String inputFile= "D:\\tmp\\tmp1\\借条_new2.pdf";
        //添加完水印的文件存放路径
        String outputFile= "D:\\tmp\\tmp1\\借条_new3.pdf";
        //需要添加的水印文字
        String waterMarkName="测试水印";
        //调用添加水印方法
        addwaterMark(inputFile, outputFile, waterMarkName);
    }

    /**
     * 添加水印的入口方法
     * @param inputFile
     * @param outputFile
     * @param waterMarkName
     * @return boolean
     */
    public static boolean addwaterMark(String inputFile,String outputFile,String waterMarkName){
        float opacity=0.1f;//水印字体透明度
        int fontsize=20;  //水印字体大小
        int angle=30;   //水印倾斜角度（0-360）
        int heightdensity=8; //数值越大每页竖向水印越少
        int widthdensity=2;   //数值越大每页横向水印越少
        return  addwaterMark(inputFile, outputFile, waterMarkName, opacity, fontsize, angle, heightdensity, widthdensity);
    }

    /**
     * 添加水印的底层方法
     * @param inputFile
     * @param outputFile
     * @param waterMarkName
     * @param opacity
     * @param fontsize
     * @param angle
     * @param heightdensity
     * @param widthdensity
     * @return boolean
     */
    public static boolean addwaterMark(String inputFile, String outputFile, String waterMarkName,
                                        float opacity, int fontsize, int angle, int heightdensity, int widthdensity) {
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            int interval = -5;
            reader = new PdfReader(inputFile);
            stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            Rectangle pageRect = null;
            PdfGState gs = new PdfGState();
            //这里是透明度设置
            gs.setFillOpacity(opacity);
            //这里是条纹不透明度
            gs.setStrokeOpacity(0.2f);
            int total = reader.getNumberOfPages() + 1;
            System.out.println("合同页数：" + reader.getNumberOfPages());
            JLabel label = new JLabel();
            FontMetrics metrics;
            int textH = 0;
            int textW = 0;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            textH = metrics.getHeight();  //字符串的高,   只和字体有关
            textW = metrics.stringWidth(label.getText());  //字符串的宽
            PdfContentByte under;
            //这个循环是确保每一张PDF都加上水印
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                under = stamper.getOverContent(i);  //在内容上方添加水印
                //under = stamper.getUnderContent(i);  //在内容下方添加水印
                under.saveState();
                under.setGState(gs);
                under.beginText();
                //under.setColorFill(BaseColor.PINK);  //添加文字颜色  不能动态改变 放弃使用
                under.setFontAndSize(base, fontsize); //这里是水印字体大小
                for (int height =  textH; height < pageRect.getHeight()*2; height = height + textH * heightdensity) {
                    for (int width =  textW; width < pageRect.getWidth()*1.5 + textW; width = width + textW * widthdensity) {
                        // rotation:倾斜角度
                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW, height - textH, angle);
                    }
                }
                //添加水印文字
                under.endText();
            }
            System.out.println("添加水印成功！");
            return true;
        } catch (IOException e) {
            System.out.println("添加水印失败！错误信息为: " + e);
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            System.out.println("添加水印失败！错误信息为: " + e);
            e.printStackTrace();
            return false;
        } finally {
            //关闭流
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
}
