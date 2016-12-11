package com.energy.comparator.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    private static final BigDecimal BASE_100 = new BigDecimal("100");

    public static BigDecimal roundByTwoDecimalPlaces(BigDecimal number) {
        return number.setScale(2, BigDecimal.ROUND_CEILING);
    }
    public static BigDecimal roundToNearestInteger(BigDecimal number) {
        return number.setScale(0, BigDecimal.ROUND_CEILING);
    }

    public static BigDecimal add(BigDecimal x, BigDecimal y){
        return x.add(y);
    }
    public static BigDecimal convertPenceToPounds(BigDecimal pence) {
        return pence.divide(BASE_100);
    }

    public static BigDecimal multiply(String x, String y){
        return new BigDecimal(x).multiply(new BigDecimal(y));
    }


}