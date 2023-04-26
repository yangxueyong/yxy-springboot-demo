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

}