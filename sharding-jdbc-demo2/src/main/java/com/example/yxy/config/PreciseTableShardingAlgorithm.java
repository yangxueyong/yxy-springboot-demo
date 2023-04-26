package com.example.yxy.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 表精确分片算法
 */
public class PreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {
 
	/**
	 * 表精确分片算法
	 *
	 * @param availableTargetNames 所有配置的表列表，这里代表所匹配到库的所有表
	 * @param shardingValue        分片值
	 * @return 所匹配表的结果
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames,
							 PreciseShardingValue<String> shardingValue) {
		// 分片键值
		String value = shardingValue.getValue();

		int valHashCode = value.hashCode();
		//此处按照value的openid % 100的正余数来分表
		int remainder = Math.floorMod(valHashCode, 100);

		for (String availableTargetName : availableTargetNames) {
			if (availableTargetName.endsWith(String.valueOf(remainder))) {
				return availableTargetName;
			}
		}
		throw new UnsupportedOperationException();
	}
}