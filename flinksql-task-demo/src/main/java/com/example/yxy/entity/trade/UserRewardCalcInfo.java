package com.example.yxy.entity.trade;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserRewardCalcInfo {

    private String type;

    private String weight;

    private List<RewardCalc> jsonDatas;

    @Data
    public static class RewardCalc {
        //概率
        private BigDecimal probability;
        private String ckTotal;
        //类型 ratio比例  random随机  fixed:固定
        private String reachType;
        //number money
        private String dataType;
        private String fieldName;
        private BigDecimal ratio;
        private BigDecimal fixedMoney;
        private BigDecimal minMoney;
        private BigDecimal maxMoney;
        private BigDecimal conditionMoney;
        private BigDecimal conditionNumber;

        public BigDecimal getRatio() {
            if (ratio == null) {
                ratio = BigDecimal.ZERO;
            }
            return ratio;
        }

        public BigDecimal getFixedMoney() {
            if (fixedMoney == null) {
                fixedMoney = BigDecimal.ZERO;
            }
            return fixedMoney;
        }

        public BigDecimal getMinMoney() {
            if (minMoney == null) {
                minMoney = BigDecimal.ZERO;
            }
            return minMoney;
        }

        public BigDecimal getMaxMoney() {
            if (maxMoney == null) {
                maxMoney = BigDecimal.ZERO;
            }
            return maxMoney;
        }

        public BigDecimal getConditionMoney() {
            if (conditionMoney == null) {
                conditionMoney = BigDecimal.ZERO;
            }
            return conditionMoney;
        }

        public BigDecimal getConditionNumber() {
            if (conditionNumber == null) {
                conditionNumber = BigDecimal.ZERO;
            }
            return conditionNumber;
        }
    }
}