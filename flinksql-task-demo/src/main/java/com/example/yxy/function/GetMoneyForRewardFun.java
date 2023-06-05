package com.example.yxy.function;

import com.example.yxy.entity.trade.UserRewardCalcInfo;
import com.example.yxy.util.BigDecimalUtils;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.table.functions.ScalarFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GetMoneyForRewardFun extends ScalarFunction {
    String REACHTYPE_RATIO = "ratio";
    String REACHTYPE_RANDOM = "random";
    String REACHTYPE_FIXED = "fixed";
    Random ran = new Random();
    BigDecimal z100 = BigDecimal.valueOf(100);
    ObjectMapper objectMapper = new ObjectMapper();
    UserRewardCalcInfo userRewardCalcInfo = null;
    List<Double> nums = null;
    double sumNum = 0;
    boolean initFalg = false;
    private void init(String value){
        UserRewardCalcInfo userRewardCalcInfo = null;
        ArrayList nums = new ArrayList<>();
        double sumNum = 0;
        try {
            userRewardCalcInfo = objectMapper.readValue(value, UserRewardCalcInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (UserRewardCalcInfo.RewardCalc rewardCalc : userRewardCalcInfo.getJsonDatas()) {
            BigDecimal probability = rewardCalc.getProbability();
            double cc = probability.doubleValue() * 100;
            sumNum += cc;
            nums.add(sumNum);
        }
        this.sumNum = sumNum;
        this.nums = nums;
        this.userRewardCalcInfo = userRewardCalcInfo;
        initFalg = true;
    }

    /**
     * 根据概率命中规则
     *
     * @return int
     */
    private UserRewardCalcInfo.RewardCalc getRuleByProbability(){
        int num = ran.nextInt((int)sumNum);
        for (int i = 0; i < nums.size(); i++) {
            Double aDouble = nums.get(i);
            if(num <= aDouble){
                return userRewardCalcInfo.getJsonDatas().get(i);
            }
        }
        return userRewardCalcInfo.getJsonDatas().get(0);
    }

    public String eval(String value,String sumMoney) {
        if(!initFalg){
            init(value);
        }
        BigDecimal sumMoney1 = BigDecimal.valueOf(Double.valueOf(sumMoney));

        UserRewardCalcInfo.RewardCalc rewardCalc = getRuleByProbability();

        String reachType = rewardCalc.getReachType();
        BigDecimal minMoney = rewardCalc.getMinMoney();
        BigDecimal maxMoney = rewardCalc.getMaxMoney();
        BigDecimal ratio = rewardCalc.getRatio();
        BigDecimal fixedMoney = rewardCalc.getFixedMoney();
        BigDecimal money = BigDecimal.ZERO;
        //按比例

        if (REACHTYPE_RATIO.equals(reachType)) {
            money = sumMoney1.multiply(ratio);
            money = money.setScale( 4, BigDecimal.ROUND_HALF_DOWN);
        } else if (REACHTYPE_RANDOM.equals(reachType)) {
            money = BigDecimal.valueOf((int)(Math.random() * sumMoney1.doubleValue() *100 + (minMoney.doubleValue()*100)));
            money = BigDecimalUtils.divide(money, BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_DOWN);
        } else if (REACHTYPE_FIXED.equals(reachType)) {
            money = fixedMoney;
        }
        if (!REACHTYPE_FIXED.equals(reachType)) {
            if (money.doubleValue() < minMoney.doubleValue()) {
                return String.valueOf(minMoney);
            } else if (money.doubleValue() > maxMoney.doubleValue()) {
                return String.valueOf(maxMoney);
            }
        }
        return String.valueOf(money);
    }

}
