package com.example.yxy.config;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 库范围分片算法
 *
 */
@Slf4j
public class RangeDatabaseShardingAlgorithm implements RangeShardingAlgorithm<String> {
 
	/**
	 * 库范围分片算法
	 *
	 * @param availableTargetNames 所有配置的库列表
	 * @param rangeShardingValue   分片值，也就是save_time_com的值，范围分片算法必须提供开始时间和结束时间
	 * @return 所匹配库的结果
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
										 RangeShardingValue<String> rangeShardingValue) {
		ArrayList<String> result = new ArrayList<>();
		Range<String> range = rangeShardingValue.getValueRange();

		// 起始年和结束年
		if(range.hasLowerBound()) {
			int startYear = ShardingAlgorithmUtil.getYearByStr(range.lowerEndpoint());
			log.info("startYear->{}",startYear);
			theSameYear(startYear, availableTargetNames, result);
		}

		if(range.hasUpperBound()) {
			int endYear = ShardingAlgorithmUtil.getYearByStr(range.upperEndpoint());
			log.info("endYear->{}",endYear);
			theSameYear(endYear, availableTargetNames, result);
		}
		if(result.size() == 0){
			return availableTargetNames;
		}
		return result;
	}
 
	/**
	 * 同一年，说明只需要一个库
	 */
	private Collection<String> theSameYear(int dateYear, Collection<String> availableTargetNames,
			ArrayList<String> result) {
		int nowYear = LocalDate.now().getYear();
		if(dateYear < nowYear){
			for (String availableTargetName : availableTargetNames) {
				if (availableTargetName.contains("his")) {
					result.add(availableTargetName);
				}
			}
		}else{
			for (String availableTargetName : availableTargetNames) {
				if (availableTargetName.contains("now")) {
					result.add(availableTargetName);
				}
			}
		}
		return result;
	}
}