package com.example.yxy.function;

import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class GetDateIntByTypeFun extends ScalarFunction {
    private String TIMETYPE_DAY = "day";
    private String TIMETYPE_WEEK = "week";
    private String TIMETYPE_MONTH = "month";
    private String TIMETYPE_QUARTER = "quarter";
    private String TIMETYPE_YEAR = "year";

    private String BEGIN = "begin";
    private String END = "end";

    public int eval(Timestamp time, String timeType, String beginOrEnd) {
        if (time == null) {
            return 0;
        }
        LocalDate date = timeToLocalDate(time);
        if(date == null){
            return 0;
        }
        boolean isFirst = false;
        if(BEGIN.equalsIgnoreCase(beginOrEnd)) {
            isFirst = true;
        }
        String dateStr = null;
        if(TIMETYPE_DAY.equalsIgnoreCase(timeType)){
            dateStr = getStartOrEndDayOfDay(date);
        }else if(TIMETYPE_WEEK.equalsIgnoreCase(timeType)){
            dateStr = getStartOrEndDayOfWeek(date, isFirst);
        }else if(TIMETYPE_MONTH.equalsIgnoreCase(timeType)){
            dateStr = getStartOrEndDayOfMonth(date, isFirst);
        }else if(TIMETYPE_QUARTER.equalsIgnoreCase(timeType)){
            dateStr = getStartOrEndDayOfQuarter(date, isFirst);
        }else if(TIMETYPE_YEAR.equalsIgnoreCase(timeType)){
            dateStr = getStartOrEndDayOfYear(date, isFirst);
        }
        return Integer.parseInt(dateStr.replaceAll("-",""));
    }


    public static LocalDate timeToLocalDate(Date time)  {
        return time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate timeToLocalDate(String time)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(time).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            return null;
        }
    }

    public static LocalDate timeToLocalDate(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    /**
     * 获取本周的开始时间与结束时间
     *
     * @param today   今天
     * @return {@link String}
     */
    public static String getStartOrEndDayOfDay(LocalDate today){
        if (today == null) {
            today = LocalDate.now();
        }
        return today.toString();
    }
    /**
     * 获取本周的开始时间与结束时间
     *
     * @param today   今天
     * @param isFirst 首先是
     * @return {@link String}
     */
    public static String getStartOrEndDayOfWeek(LocalDate today, Boolean isFirst){
        if (today == null) {
            today = LocalDate.now();
        }
        if (isFirst) {
            return today.with(DayOfWeek.MONDAY).toString();
        } else {
            return today.with(DayOfWeek.SUNDAY).toString();
        }
    }

    /**
     * 获取月的开始时间与结束时间
     *
     * @param today   今天
     * @param isFirst 首先是
     * @return {@link String}
     */
    public static String getStartOrEndDayOfMonth(LocalDate today, Boolean isFirst){
        if (today == null) {
            today = LocalDate.now();
        }
//        Month month = today.getMonth();
//        int length = month.length(today.isLeapYear());
        if (isFirst) {
            today = today.with(TemporalAdjusters.firstDayOfMonth());
        } else {
            today =  today.with(TemporalAdjusters.lastDayOfMonth());
        }
        return today.toString();
    }

    /**
     * 获取季度的开始时间与结束时间
     *
     * @param today   今天
     * @param isFirst 首先是
     * @return {@link String}
     */
    public static String getStartOrEndDayOfQuarter(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), firstMonthOfQuarter, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(today.isLeapYear()));
        }
        return resDate.toString();
    }

    /**
     * 获取年的开始时间与结束时间
     *
     * @param today   今天
     * @param isFirst 首先是
     * @return {@link String}
     */
    public static String getStartOrEndDayOfYear(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        if (isFirst) {
            resDate =  today.with(TemporalAdjusters.firstDayOfYear());
        } else {
            resDate =  today.with(TemporalAdjusters.lastDayOfYear());
        }
        return resDate.toString();
    }

}
    