package com.example.yxy.config;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * 表范围分片算法
 *
 */
public class RangeTableShardingAlgorithm implements RangeShardingAlgorithm<String> {
 
	/**
	 * 表范围分片算法
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
										 RangeShardingValue<String> rangeShardingValue) {
		Range<String> range = rangeShardingValue.getValueRange();
		int startMillisecond = range.lowerEndpoint().hashCode();
		int endMillisecond = range.upperEndpoint().hashCode();
		return getMonthBetween(startMillisecond, endMillisecond, availableTargetNames);
	}
 
	/**
	 * 计算有效的库表名
	 */
	public static Collection<String> getMonthBetween(int minTime, int maxTime,
			Collection<String> availableTargetNames) {
		Collection<String> result = new ArrayList<>();

		int min1 = Math.floorMod(minTime, 100);
		int max1 = Math.floorMod(maxTime, 100);

		for (int i = min1; i <= max1; i++) {
			for (String availableTargetName : availableTargetNames) {
				if (availableTargetName.endsWith(String.valueOf(i))) {
					result.add(availableTargetName);
				}
			}
		}
		return result;
	}
}