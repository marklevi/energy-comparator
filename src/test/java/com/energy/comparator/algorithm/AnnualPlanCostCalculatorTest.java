package com.energy.comparator.algorithm;

import com.energy.comparator.domain.Plan;
import com.energy.comparator.domain.PriceThreshold;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AnnualPlanCostCalculatorTest {

    @Test
    public void shouldCalculateAnnualPlanCostWithoutThreshold() throws Exception {
        AnnualPlanCostCalculator annualPlanCostCalculator = new AnnualPlanCostCalculator();

        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("10.0"), Optional.empty());
        List<PriceThreshold> rates = Collections.singletonList(priceWithoutThreshold);

        Plan plan = new Plan("eon", "variable", rates, Optional.empty());

        BigDecimal annualPlanCost = annualPlanCostCalculator.calculate(plan, new BigDecimal("1000"));

        assertThat(annualPlanCost, is(new BigDecimal("10000.0")));

    }

    @Test
    public void shouldCalculateAnnualPlanCostWithThreshold() throws Exception {
        AnnualPlanCostCalculator annualPlanCostCalculator = new AnnualPlanCostCalculator();

        PriceThreshold priceWithThreshold = new PriceThreshold(new BigDecimal("20.0"), Optional.of(new BigDecimal("50")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("10.0"), Optional.empty());

        List<PriceThreshold> rates = Arrays.asList(priceWithThreshold, priceWithoutThreshold);

        Plan plan = new Plan("eon", "variable", rates, Optional.empty());

        BigDecimal annualPlanCost = annualPlanCostCalculator.calculate(plan, new BigDecimal("100"));

        assertThat(annualPlanCost, is(new BigDecimal("1500.0")));

    }

    @Test
    public void shouldChargeStartingFromFirstThreshold() throws Exception {
        AnnualPlanCostCalculator annualPlanCostCalculator = new AnnualPlanCostCalculator();

        PriceThreshold priceWithThreshold = new PriceThreshold(new BigDecimal("20.0"), Optional.of(new BigDecimal("50")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("10.0"), Optional.empty());

        List<PriceThreshold> rates = Arrays.asList(priceWithThreshold, priceWithoutThreshold);

        Plan plan = new Plan("eon", "variable", rates, Optional.empty());

        BigDecimal annualPlanCost = annualPlanCostCalculator.calculate(plan, new BigDecimal("10"));

        assertThat(annualPlanCost, is(new BigDecimal("200.0")));

    }

    @Test
    public void shouldCalculateAnnualPlanCostWithMoreThanOneThreshold() throws Exception {
        AnnualPlanCostCalculator annualPlanCostCalculator = new AnnualPlanCostCalculator();

        List<PriceThreshold> rates = getRates();

        Plan plan = new Plan("eon", "variable", rates, Optional.empty());

        BigDecimal annualPlanCost = annualPlanCostCalculator.calculate(plan, new BigDecimal("100"));

        assertThat(annualPlanCost, is(new BigDecimal("1650.0")));

    }

    @Test
    public void shouldCalculateAnnualPlanCostWithStandingCharge() throws Exception {
        AnnualPlanCostCalculator annualPlanCostCalculator = new AnnualPlanCostCalculator();

        List<PriceThreshold> rates = getRates();

        Plan plan = new Plan("eon", "variable", rates, Optional.of(new BigDecimal("1")));

        BigDecimal annualPlanCost = annualPlanCostCalculator.calculate(plan, new BigDecimal("100"));

        assertThat(annualPlanCost, is(new BigDecimal("2015.0")));

    }

    private List<PriceThreshold> getRates() {
        PriceThreshold priceWithThresholdOne = new PriceThreshold(new BigDecimal("20.0"), Optional.of(new BigDecimal("50")));
        PriceThreshold priceWithThresholdTwo = new PriceThreshold(new BigDecimal("15.0"), Optional.of(new BigDecimal("30")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("10.0"), Optional.empty());

        return Arrays.asList(priceWithThresholdOne, priceWithThresholdTwo, priceWithoutThreshold);
    }
}