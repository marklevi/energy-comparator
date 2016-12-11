package com.energy.comparator.parser;

import com.energy.comparator.Plan;
import com.energy.comparator.PriceThreshold;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlanDTO {

    @JsonProperty
    private String supplier;

    @JsonProperty("plan")
    private String type;

    @JsonProperty
    private List<PriceThresholdDTO> rates;

    @JsonProperty("standing_charge")
    private BigDecimal standingCharge;

    public PlanDTO() {
        /* for jackson */
    }

    public Plan build(){
        List<PriceThreshold> rates = this.rates.stream().map(PriceThresholdDTO::build).collect(Collectors.toList());
        return new Plan(supplier, type, rates, Optional.ofNullable(standingCharge));
    }
}
