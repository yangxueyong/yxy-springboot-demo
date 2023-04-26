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
 * 例如 select  * from tab where time>='2023-10-10'
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
		//范围值
		/**
		 * 比如select * from tab where time>='2020-10-01' 则range中的lowerEndpoint就为2020-10-01 upperEndpoint为空
		 * 比如select * from tab where time<='2020-10-01' 则range中的upperEndpoint就为2020-10-01 lowerEndpoint为空
		 */
		Range<String> range = rangeShardingValue.getValueRange();

		// 如果有起始数据
		if(range.hasLowerBound()) {
			int startYear = ShardingAlgorithmUtil.getYearByStr(range.lowerEndpoint());
			log.info("startYear->{}",startYear);
			theSameYear(startYear, availableTargetNames, result);
		}

		//如果有终止数据
		if(range.hasUpperBound()) {
			int endYear = ShardingAlgorithmUtil.getYearByStr(range.upperEndpoint());
			log.info("endYear->{}",endYear);
			theSameYear(endYear, availableTargetNames, result);
		}

		//如果起止数据都没有，则返回所有的库表
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
		//如果要查询的年不是今年，则说明需要查询历史库
		if(dateYear < nowYear){
			for (String availableTargetName : availableTargetNames) {
				if (availableTargetName.contains("his")) {
					result.add(availableTargetName);
				}
			}

			//如果要查询的年是今年，则直接查询现在的交易库
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