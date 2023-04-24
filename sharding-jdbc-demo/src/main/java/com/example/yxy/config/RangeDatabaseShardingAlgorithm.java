package com.example.yxy.config;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 库范围分片算法
 *
 */
public class RangeDatabaseShardingAlgorithm implements RangeShardingAlgorithm<Long> {
 
	/**
	 * 库范围分片算法
	 *
	 * @param availableTargetNames 所有配置的库列表
	 * @param rangeShardingValue   分片值，也就是save_time_com的值，范围分片算法必须提供开始时间和结束时间
	 * @return 所匹配库的结果
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
										 RangeShardingValue<Long> rangeShardingValue) {
		ArrayList<String> result = new ArrayList<>();
		Range<Long> range = rangeShardingValue.getValueRange();
		// 起始年和结束年
		int startYear = Integer.parseInt(ShardingAlgorithmUtil.getYearByMillisecond(range.lowerEndpoint()));
		int endYear = Integer.parseInt(ShardingAlgorithmUtil.getYearByMillisecond(range.upperEndpoint()));
		return startYear == endYear ? theSameYear(String.valueOf(startYear), availableTargetNames, result)
				: differentYear(startYear, endYear, availableTargetNames, result);
	}
 
	/**
	 * 同一年，说明只需要一个库
	 */
	private Collection<String> theSameYear(String startTime, Collection<String> availableTargetNames,
			ArrayList<String> result) {
		for (String availableTargetName : availableTargetNames) {
			if (availableTargetName.endsWith(startTime))
				result.add(availableTargetName);
		}
		return result;
	}
	/**
	 * 跨年
	 */
	private Collection<String> differentYear(int startYear, int endYear, Collection<String> availableTargetNames,
			ArrayList<String> result) {
		for (String availableTargetName : availableTargetNames) {
			for (int i = startYear; i <= endYear; i++) {
				if (availableTargetName.endsWith(String.valueOf(i)))
					result.add(availableTargetName);
			}
		}
		return result;
	}
}