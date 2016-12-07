package com.energy.comparator;

import java.math.BigDecimal;

import static java.lang.String.format;

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
    public String toString() {
        return format("%s,%s,%s", supplier, type, cost);
    }

    @Override
    public int compareTo(AnnualPlanCost o) {
        return this.cost.compareTo(o.cost);
    }
}
