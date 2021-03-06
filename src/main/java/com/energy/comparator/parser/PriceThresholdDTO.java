package com.energy.comparator.parser;

import com.energy.comparator.domain.PriceThreshold;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Optional;

public class PriceThresholdDTO {

    @JsonProperty
    private BigDecimal price;

    @JsonProperty
    private BigDecimal threshold;

    public PriceThresholdDTO() {
        /* for jackson */
    }

    public PriceThreshold build(){
        return new PriceThreshold(price, Optional.ofNullable(threshold));
    }

}
