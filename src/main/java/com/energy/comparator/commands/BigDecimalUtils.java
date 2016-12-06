package com.energy.comparator.commands;

import java.math.BigDecimal;

public class BigDecimalUtils {

    private static BigDecimal VAT = new BigDecimal("0.05");
    private static final BigDecimal BASE_100 = new BigDecimal("100");

    public static BigDecimal roundByTwoDecimalPlaces(BigDecimal vatIncludedPriceInDollar) {
        return vatIncludedPriceInDollar.setScale(2, BigDecimal.ROUND_CEILING);
    }

    public static BigDecimal convertPenceToPounds(BigDecimal vatIncludedPriceInPence) {
        return vatIncludedPriceInPence.divide(BASE_100);
    }

    public static BigDecimal applyVAT(BigDecimal vatExcludedPriceInPence) {
        BigDecimal vatAmount = vatExcludedPriceInPence.multiply(VAT);
        return vatExcludedPriceInPence.add(vatAmount);
    }


}