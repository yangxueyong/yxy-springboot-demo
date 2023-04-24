package com.example.yxy.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 表精确分片算法
 */
public class PreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
 
	/**
	 * 表精确分片算法
	 *
	 * @param availableTargetNames 所有配置的表列表，这里代表所匹配到库的所有表
	 * @param shardingValue        分片值
	 * @return 所匹配表的结果
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames,
							 PreciseShardingValue<Long> shardingValue) {
		// 分片键值
		long value = shardingValue.getValue();
 
		if (value <= 0) {
			throw new UnsupportedOperationException("preciseShardingValue is null");
		}
 
		String yearJoinMonthStr = ShardingAlgorithmUtil.getYearJoinMonthByMillisecond(value);
		for (String availableTargetName : availableTargetNames) {
			if (availableTargetName.endsWith(yearJoinMonthStr)) {
				return availableTargetName;
			}
		}
		throw new UnsupportedOperationException();
	}
}