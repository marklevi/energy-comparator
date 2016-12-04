package com.uswitch.comparator;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AnnualPlanCostCalculatorTest {

    @Test
    public void shouldCalculateAnnualPlanCost() throws Exception {
        AnnualPlanCostCalculator annualPlanCostCalculator = new AnnualPlanCostCalculator();

        PriceThreshold priceThreshold = new PriceThreshold(new BigDecimal("13.5"), Optional.of(new BigDecimal("100")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("10.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceThreshold, priceWithoutThreshold);

        Plan plan = new Plan("eon", "variable", rates, Optional.empty());

        AnnualPlanCost annualPlanCost = annualPlanCostCalculator.calculate(plan, new BigDecimal("1000"));

        assertThat(annualPlanCost.getCost(), is(new BigDecimal("108.68")));
        assertThat(annualPlanCost.getSupplier(), is("eon"));
        assertThat(annualPlanCost.getType(), is("variable"));

    }
}