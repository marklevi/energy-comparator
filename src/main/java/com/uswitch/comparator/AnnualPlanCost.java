package com.uswitch.comparator;

import java.math.BigDecimal;
import java.util.Comparator;

public class AnnualPlanCost implements Comparable<AnnualPlanCost> {
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

    @Override
    public int compareTo(AnnualPlanCost o) {
        return this.cost.compareTo(o.cost);
    }
}
