package com.example.yxy.config;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 分片算法工具类
 */
public class ShardingAlgorithmUtil {

	/**
	 * 获取年份
	 */
	public static int getYearByStr(String dayStr) {
		TemporalAccessor parse = DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(dayStr);
		return parse.get(ChronoField.YEAR);
	}

	/**
	 * 获取年份
	 */
	public static String getYearByMillisecond(long millisecond) {
		return new SimpleDateFormat("yyyy").format(new Date(millisecond));
	}
 
	/**
	 * 获取年月
	 */
	public static String getYearJoinMonthByMillisecond(long millisecond) {
		return new SimpleDateFormat("yyyyMM").format(new Date(millisecond));
	}
}