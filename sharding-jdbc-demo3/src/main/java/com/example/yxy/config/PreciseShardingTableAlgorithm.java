package com.example.yxy.config;
 
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
 
/**
 * @description sharding jdbc 精准`分表`策略
 **/
@Slf4j
public class PreciseShardingTableAlgorithm implements StandardShardingAlgorithm<String> {
 
    // 分表数量
//    private static int tableSize;
//
//    @Value("${tableSize}")
//    public void setTableSize(int size) {
//        tableSize = size;
//    }
 
    /**
     * @description: 分表策略，按用户编号倒数二三位数字对数据库表数量取模
     *
     * @param tableNames 所有表名
     * @param preciseShardingValue 精确分片值，包括（columnName，logicTableName，value）
     * @return 表名
     */
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<String> preciseShardingValue) {
 
//        log.info("Table PreciseShardingAlgorithm tableNames:{} ,preciseShardingValue: {}.",
//                JSON.toJSONString(tableNames), JSON.toJSONString(preciseShardingValue));
//        // 按表数量取模
//        // 截取用户编号倒数二三位数字，（如1234的倒数二三位为23）
//        String num = StringUtils.substring(preciseShardingValue.getValue(), -3, -1);
//        int mod = Integer.parseInt(num) % tableSize;
//        for (String tableName : tableNames) {
//            // 分表的规则
//            if (tableName.endsWith(String.valueOf(mod))) {
//                return tableName;
//            }
//        }
//        throw new UnsupportedOperationException();

        // 分片键值
        String value = preciseShardingValue.getValue();

        int valHashCode = value.hashCode();
        //此处按照value的openid % 100的正余数来分表
        int remainder = Math.floorMod(valHashCode, 100);

        for (String availableTargetName : tableNames) {
            if (availableTargetName.endsWith(String.valueOf(remainder))) {
                return availableTargetName;
            }
        }
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> rangeShardingValue) {
        Range<String> range = rangeShardingValue.getValueRange();
        int startHashCode = 0;
        if(range.hasLowerBound()) {
            startHashCode = range.lowerEndpoint().hashCode();
        }
        int endHashCode = 99;
        if(range.hasUpperBound()) {
            endHashCode = range.upperEndpoint().hashCode();
        }
        return getMonthBetween(startHashCode, endHashCode, availableTargetNames);
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
 
    @Override
    public Properties getProps() {
        return null;
    }
 
    @Override
    public void init(Properties properties) {
 
    }

    @Override
    public String getType() {
        return "my_table_sharding";
    }

}