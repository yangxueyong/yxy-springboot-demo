package com.example.yxy.config;
 
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
 
/**
 * @description sharding jdbc 精准 `分库` 策略
 **/
@Slf4j
public class PreciseShardingDatabaseAlgorithm implements StandardShardingAlgorithm<String> {
 
//    // 主库别名
//    private static final String DBM = "dbm";
//
//    private static int dataBaseSize;
//
//    @Value("${dataBaseSize}")
//    public void setDataBaseSize(int size) {
//        dataBaseSize = size;
//    }
 
    /**
     * @description: 分库策略，按用户编号最后一位数字对数据库数量取模
     *
     * @param dbNames 所有库名
     * @param shardingValue 精确分片值，包括（columnName，logicTableName，value）
     * @return 表名
     */
    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<String> shardingValue) {
        log.info("Database PreciseShardingAlgorithm dbNames:{} ,preciseShardingValue: {}.", JSON.toJSONString(dbNames),
                JSON.toJSONString(shardingValue));
 
//        // 若走主库，直接返回主库
//        if (dbNames.size() == 1) {
//            Iterator<String> iterator = dbNames.iterator();
//            String dbName = iterator.next();
//            if (DBM.equals(dbName)) {
//                return DBM;
//            }
//        }
//
//        // 按数据库数量取模
//        String num = StringUtils.substring(preciseShardingValue.getValue(), -1);
//        int mod = Integer.parseInt(num) % dataBaseSize;
//        for (String dbName : dbNames) {
//            // 分库的规则
//            if (dbName.endsWith(String.valueOf(mod))) {
//                return dbName;
//            }
//        }
//        throw new UnsupportedOperationException();

        // 分片键值
        String value = shardingValue.getValue();
        int nowYear = LocalDate.now().getYear();
        // 库后缀
        int dateYear = ShardingAlgorithmUtil.getYearByStr(value);
        //如果目标年比当前时间小 就去历史库看
        if(dateYear < nowYear){
            for (String availableTargetName : dbNames) {
                if (availableTargetName.contains("his")) {
                    return availableTargetName;
                }
            }
        }else{
            for (String availableTargetName : dbNames) {
                if (availableTargetName.contains("now")) {
                    return availableTargetName;
                }
            }
        }
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> rangeShardingValue) {
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

    @Override
    public Properties getProps() {
        return null;
    }
 
    @Override
    public void init(Properties properties) {
 
    }

    @Override
    public String getType() {
        return "my_db_sharding";
    }
}