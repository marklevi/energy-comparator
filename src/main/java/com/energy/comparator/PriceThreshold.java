package com.energy.comparator;

import java.math.BigDecimal;
import java.util.Optional;

public class PriceThreshold {
    private BigDecimal price;
    private Optional<BigDecimal> threshold;

    public PriceThreshold(BigDecimal price, Optional<BigDecimal> threshold) {
        this.price = price;
        this.threshold = threshold;
    }

    public Optional<BigDecimal> getThresholdValue() {
        return threshold;
    }

    public BigDecimal getPricePerKilowattHour() {
        return price;
    }
}

