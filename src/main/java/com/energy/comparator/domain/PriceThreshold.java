package com.energy.comparator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

