package com.energy.comparator.domain;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Plan {
    private String supplier;
    private String type;
    private List<PriceThreshold> rates;
    private Optional<BigDecimal> standingCharge;

    public Plan(String supplier, String type, List<PriceThreshold> rates, Optional<BigDecimal> standingCharge) {
        this.supplier = supplier;
        this.type = type;
        this.rates = rates;
        this.standingCharge = standingCharge;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getType() {
        return type;
    }

    public List<PriceThreshold> getRates() {
        return rates;
    }

    public int getRatesSize() {
        return rates.size();
    }

    public Iterator<PriceThreshold> getRate(){
        return rates.iterator();
    }

    public Optional<BigDecimal> getStandingCharge() {
        return standingCharge;
    }
}

