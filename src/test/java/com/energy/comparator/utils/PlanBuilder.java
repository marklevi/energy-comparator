package com.energy.comparator.utils;

import com.energy.comparator.domain.Plan;
import com.energy.comparator.domain.PriceThreshold;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PlanBuilder {

    public static List<Plan> getPlans(){
        Plan planOne = getPlanOne();
        Plan planTwo = getPlanTwo();
        Plan planThree = getPlanThree();
        Plan planFour = getPlanFour();

        return Arrays.asList(planOne, planTwo, planThree, planFour);
    }

    private static Plan getPlanFour() {
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("9.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceWithoutThreshold);
        return new Plan("bg", "standing-charge", rates, Optional.of(new BigDecimal("7.0")));
    }

    private static  Plan getPlanThree() {
        PriceThreshold priceThresholdOne = new PriceThreshold(new BigDecimal("14.5"), Optional.of(new BigDecimal("250")));
        PriceThreshold priceThresholdTwo = new PriceThreshold(new BigDecimal("10.1"), Optional.of(new BigDecimal("200")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("9.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceThresholdOne, priceThresholdTwo, priceWithoutThreshold);
        return new Plan("edf", "fixed", rates, Optional.empty());
    }

    private static Plan getPlanTwo() {
        PriceThreshold priceThreshold = new PriceThreshold(new BigDecimal("12.5"), Optional.of(new BigDecimal("300")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("11.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceThreshold, priceWithoutThreshold);
        return new Plan("ovo", "standard", rates, Optional.empty());
    }

    private static Plan getPlanOne() {
        PriceThreshold priceThreshold = new PriceThreshold(new BigDecimal("13.5"), Optional.of(new BigDecimal("100")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("10.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceThreshold, priceWithoutThreshold);
        return new Plan("eon", "variable", rates, Optional.empty());
    }
}
