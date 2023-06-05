package com.example.yxy.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * BigDecimal工具类
 * @author yangxueyong
 *
 */
public class BigDecimalUtils {
    
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;
    
    //建立货币格式化引用 
    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    
    //建立百分比格式化引用 
    private static final NumberFormat percent = NumberFormat.getPercentInstance();

    private static final NumberFormat percent_2 = NumberFormat.getPercentInstance();

    static{
        percent_2.setMinimumFractionDigits(2);
    }

    /**
     * 是否为空，为空的话则默认为0
     * @param num
     * @return
     */
    public static BigDecimal defZero(BigDecimal num) {
        if(num == null){
            return BigDecimal.ZERO;
        }
        return num;
    }
    /**
     * 加法
     * @param num
     * @param num1
     * @return
     */
    public static BigDecimal add(BigDecimal num, BigDecimal num1) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.add(num1);
    }

    /**
     * 加法
     * @param nums
     * @return
     */
    public static BigDecimal adds(BigDecimal ... nums) {
        BigDecimal sum = BigDecimal.ZERO;
        for(BigDecimal num : nums){
            if( num == null){
                num = BigDecimal.ZERO;
            }
            sum = add(sum,num);
        }
        return sum;
    }

    
    /**
     * 提供精确的加法运算(默认四舍五入，根据scale保留小数位数)
     * @param num
     * @param num1
     * @param scale
     * @return
     */
    public static BigDecimal add(BigDecimal num, BigDecimal num1, int scale) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.add(num1).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    
    /**
     * 提供精确的加法运算(默认四舍五入，根据scale保留小数位数)
     * @param add
     * @param add1
     * @param scale
     * @return
     */
    public static BigDecimal add(String add, String add1, int scale) {
        BigDecimal num = new BigDecimal(add);
        BigDecimal num1 = new BigDecimal(add1);
        return num.add(num1).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    
    /**
     * 减法
     * @param num
     * @param num1
     * @return
     */
    public static BigDecimal sub(BigDecimal num, BigDecimal num1) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.subtract(num1);
    }
    
    
    /**
     * 提供精确的减法运算(默认四舍五入，根据scale保留小数位数)
     * @param num
     * @param num1
     * @param scale
     * @return
     */
    public static BigDecimal sub(BigDecimal num, BigDecimal num1, int scale) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.subtract(num1).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    
    /**
     * 提供精确的减法运算(默认四舍五入，根据scale保留小数位数)
     * @param minus
     * @param minus1
     * @return
     */
    public static BigDecimal sub(String minus, String minus1, int scale) {
        BigDecimal num = new BigDecimal(minus);
        BigDecimal num1 = new BigDecimal(minus1);
        return sub(num, num1, scale);
    }
    
    
    /**
     * 乘法
     * @param num
     * @param num1
     * @return
     */
    public static BigDecimal multiply(BigDecimal num, BigDecimal num1) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.multiply(num1);
    }
    
    
    /**
     * 提供精确的乘法运算(默认四舍五入,保留小数位数根据scale决定)
     * @param num
     * @param num1
     * @param scale
     * @return
     */
    public static BigDecimal multiply(String num, String num1, int scale) {
        BigDecimal mul = new BigDecimal(num);
        BigDecimal mul1 = new BigDecimal(num1);
        return multiply(mul, mul1, scale);
    }
    
    
    /**
     * 提供精确的乘法运算(默认四舍五入，保留小数位数根据scale确定)
     * @param num
     * @param num1
     * @param scale
     * @return
     */
    public static BigDecimal multiply(BigDecimal num, BigDecimal num1, int scale) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.multiply(num1).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal multiply(BigDecimal num, BigDecimal num1, int scale, int round) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.multiply(num1).setScale(scale, round);
    }



    /**
     * 除法(除法除不尽会抛异常)
     * @param num
     * @param num1
     * @return
     */
    public static BigDecimal divide(BigDecimal num, BigDecimal num1) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.divide(num1, DEF_DIV_SCALE);
    }
    
    
    /**
     * 提供精确的除法运算(默认四舍五入保留两位小数)
     * @param dividend
     * @param divisor
     * @return
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale) {
        return dividend.divide(divisor, scale, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 百分比计算
     * @param dividend
     * @param divisor
     * @return
     */
    public static String percent_2(BigDecimal dividend, BigDecimal divisor) {
        BigDecimal divide = divide(dividend, divisor, 4, BigDecimal.ROUND_HALF_UP);
//        DecimalFormat df = new DecimalFormat("0.00%");
        String format = rateFormat_2(divide);
        return format;
    }

    /**
     * 提供精确的除法运算(默认四舍五入，保留小数位数根据scale决定)
     * @param dividend
     * @param divisor
     * @param scale
     * @return
     */
    public static BigDecimal divide(String dividend, String divisor, int scale) {
        BigDecimal num = new BigDecimal(dividend);
        BigDecimal num1 = new BigDecimal(divisor);
        return divide(num, num1, scale);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor,int keep, int scale) {
        if(dividend == null){
            dividend = BigDecimal.ZERO;
        }
        return dividend.divide(divisor,keep,scale);
    }


    /**
     * 提供精确的取余数运算(小数保留位数根据scale决定)
     * @param dividend
     * @param divisor
     * @param scale
     * @return
     */
    public static BigDecimal balance(BigDecimal dividend, BigDecimal divisor, int scale) {
        return dividend.remainder(divisor).setScale(scale);
    }
    
    
    /**
     * 提供精确的取余数运算(默认保留两位小数)
     * @param dividend
     * @param divisor
     * @return
     */
    public static BigDecimal balance(BigDecimal dividend, BigDecimal divisor) {
        return dividend.remainder(divisor).setScale(DEF_DIV_SCALE);
    }
    
    
    /**
     * 比较BigDecimal,相等返回0,num>num1返回1,num<num1返回-1
     * @param num
     * @param num1
     * @return
     */
    public static int compareTo(BigDecimal num, BigDecimal num1) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.compareTo(num1);
    }

    /**
     * 是否大于
     * @param num
     * @param num1
     * @return
     */
    public static boolean numGtnum(BigDecimal num, BigDecimal num1) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.compareTo(num1) == 1;
    }

    /**
     * 是否大于等于
     * @param num
     * @param num1
     * @return
     */
    public static boolean numGtEqnum(BigDecimal num, BigDecimal num1) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.compareTo(num1) > -1;
    }

    /**
     * 是否等于
     * @param num
     * @param num1
     * @return
     */
    public static boolean numEqnum(BigDecimal num, BigDecimal num1) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        if(num1 == null){
            num1 = BigDecimal.ZERO;
        }
        return num.compareTo(num1) == 0;
    }

    /**
     * 比较BigDecimal,相等返回true
     * @param num
     * @param num
     * @return
     */
    public static boolean compareToZero(BigDecimal num) {
        if(num == null){
            num = BigDecimal.ZERO;
        }
        return compareTo(BigDecimal.ZERO,num) == 0;
    }

    
    /**
     * BigDecimal货币格式化
     * @param money
     * @return
     */
    public static String currencyFormat(BigDecimal money) {
        return currency.format(money);
    }
    
    
    /**
     * BigDecimal百分比格式化
     * @param rate
     * @return
     */
    public static String rateFormat(BigDecimal rate) {
        return percent.format(rate); 
    }

    public static String rateFormat_2(BigDecimal rate) {
        return percent_2.format(rate);
    }


}