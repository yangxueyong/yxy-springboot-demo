package com.example.yxy.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 库精确分片算法 insert ,select * from money_flow where time='2020-'
 *
 */
public class PreciseDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
 
	/**
	 * 库精确分片算法
	 *
	 * @param availableTargetNames 所有配置的库列表
	 * @param shardingValue        分片值
	 * @return 所匹配库的结果
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames,
							 PreciseShardingValue<Long> shardingValue) {
		// 分片键值
		Long value = shardingValue.getValue();
 
		// 库后缀
		String yearStr = ShardingAlgorithmUtil.getYearByMillisecond(value);
		if (value <= 0) {
			throw new UnsupportedOperationException("preciseShardingValue is null");
		}
		for (String availableTargetName : availableTargetNames) {
			if (availableTargetName.endsWith(yearStr)) {
				return availableTargetName;
			}
		}
		throw new UnsupportedOperationException();
	}
}