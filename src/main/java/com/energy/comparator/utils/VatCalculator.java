package com.energy.comparator.utils;

import java.math.BigDecimal;

public class VatCalculator {

    public static BigDecimal VAT = new BigDecimal("0.05");

    public static BigDecimal calculateVat(BigDecimal vatExcludedPriceInPence, BigDecimal vat) {
        return vatExcludedPriceInPence.multiply(vat);
    }
}