package com.energy.comparator.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    private static BigDecimal VAT = new BigDecimal("0.05");
    private static final BigDecimal BASE_100 = new BigDecimal("100");

    public static BigDecimal roundByTwoDecimalPlaces(BigDecimal number) {
        return number.setScale(2, BigDecimal.ROUND_CEILING);
    }
    public static BigDecimal roundToNearestInteger(BigDecimal number) {
        return number.setScale(0, BigDecimal.ROUND_CEILING);
    }

    public static BigDecimal convertPenceToPounds(BigDecimal pence) {
        return pence.divide(BASE_100);
    }

    public static BigDecimal applyVAT(BigDecimal vatExcludedPriceInPence) {
        BigDecimal vatAmount = vatExcludedPriceInPence.multiply(VAT);
        return vatExcludedPriceInPence.add(vatAmount);
    }


}