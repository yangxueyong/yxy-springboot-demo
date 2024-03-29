package com.example.yxy.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * 库精确分片算法
 * 例如 select  * from tab where time='2023-10-10'
 */
public class PreciseDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {
 
	/**
	 * 库精确分片算法
	 *
	 * @param availableTargetNames 所有配置的库列表
	 * @param shardingValue        分片值
	 * @return 所匹配库的结果
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames,
							 PreciseShardingValue<String> shardingValue) {
		// 分片键值
		String value = shardingValue.getValue();
		int nowYear = LocalDate.now().getYear();
		// 库后缀
		int dateYear = ShardingAlgorithmUtil.getYearByStr(value);
		//如果目标年比当前时间小 就去历史库看
		if(dateYear < nowYear){
			for (String availableTargetName : availableTargetNames) {
				if (availableTargetName.contains("his")) {
					return availableTargetName;
				}
			}
		}else{
			for (String availableTargetName : availableTargetNames) {
				if (availableTargetName.contains("now")) {
					return availableTargetName;
				}
			}
		}
		throw new UnsupportedOperationException();
	}
}