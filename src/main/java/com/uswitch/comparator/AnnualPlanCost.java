package com.uswitch.comparator;

import java.math.BigDecimal;

public class AnnualPlanCost {
    private String supplier;
    private String type;
    private BigDecimal cost;

    public AnnualPlanCost(String supplier, String type, BigDecimal cost) {
        this.supplier = supplier;
        this.type = type;
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getType() {
        return type;
    }

    public String getSupplier() {
        return supplier;
    }
}
