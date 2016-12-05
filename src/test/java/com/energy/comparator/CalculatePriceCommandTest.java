package com.energy.comparator;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatePriceCommandTest {

    private CalculatePriceCommand calculatePriceCommand;

    @Before
    public void setUp() throws Exception {
        List<Plan> plans = getPlans();
        calculatePriceCommand = new CalculatePriceCommand(plans);
    }

    @Test
    public void shouldBeResponsibleOfProcessingLine() throws Exception {

        boolean shouldBeTrue = calculatePriceCommand.test("price 1000");
        assertThat(shouldBeTrue, is(true));
    }

    @Test
    public void shouldNotBeResponsibleOfProcessingLine() throws Exception {

        boolean shouldBeTrue = calculatePriceCommand.test("usage edf fixed 350");
        assertThat(shouldBeTrue, is(false));
    }

    @Test
    public void shouldCalculatePricesForAllPlans() throws Exception {

        List<AnnualPlanCost> annualPlanCosts = calculatePriceCommand.process("price 1000");

        AnnualPlanCost annualPlanCostForOvo = annualPlanCosts.stream().filter(p -> p.getSupplier().equals("ovo")).findFirst().get();
        assertThat(annualPlanCostForOvo.getCost(), is(new BigDecimal("120.23")));

        AnnualPlanCost annualPlanCostForEon = annualPlanCosts.stream().filter(p -> p.getSupplier().equals("eon")).findFirst().get();
        assertThat(annualPlanCostForEon.getCost(), is(new BigDecimal("108.68")));

        AnnualPlanCost annualPlanCostForEdf = annualPlanCosts.stream().filter(p -> p.getSupplier().equals("edf")).findFirst().get();
        assertThat(annualPlanCostForEdf.getCost(), is(new BigDecimal("111.25")));

        AnnualPlanCost annualPlanCostForBg = annualPlanCosts.stream().filter(p -> p.getSupplier().equals("bg")).findFirst().get();
        assertThat(annualPlanCostForBg.getCost(), is(new BigDecimal("121.33")));
    }

    @Test
    public void shouldRankPricesFromTheCheapestToTheMostExpensive() throws Exception {
        List<AnnualPlanCost> annualPlanCosts = calculatePriceCommand.process("price 1000");
        assertThat(annualPlanCosts.get(0).getSupplier(), is("eon"));
        assertThat(annualPlanCosts.get(1).getSupplier(), is("edf"));
        assertThat(annualPlanCosts.get(2).getSupplier(), is("ovo"));
        assertThat(annualPlanCosts.get(3).getSupplier(), is("bg"));
    }

    private List<Plan> getPlans() {
        Plan planOne = getPlanOne();
        Plan planTwo = getPlanTwo();
        Plan planThree = getPlanThree();
        Plan planFour = getPlanFour();

        return Arrays.asList(planOne, planTwo, planThree, planFour);
    }

    private Plan getPlanFour() {
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("9.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceWithoutThreshold);
        return new Plan("bg", "standing-charge", rates, Optional.of(new BigDecimal("7.0")));
    }

    private Plan getPlanThree() {
        PriceThreshold priceThresholdOne = new PriceThreshold(new BigDecimal("14.5"), Optional.of(new BigDecimal("250")));
        PriceThreshold priceThresholdTwo = new PriceThreshold(new BigDecimal("10.1"), Optional.of(new BigDecimal("200")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("9.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceThresholdOne, priceThresholdTwo, priceWithoutThreshold);
        return new Plan("edf", "fixed", rates, Optional.empty());
    }

    private Plan getPlanTwo() {
        PriceThreshold priceThreshold = new PriceThreshold(new BigDecimal("12.5"), Optional.of(new BigDecimal("300")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("11.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceThreshold, priceWithoutThreshold);
        return new Plan("ovo", "standard", rates, Optional.empty());
    }

    private Plan getPlanOne() {
        PriceThreshold priceThreshold = new PriceThreshold(new BigDecimal("13.5"), Optional.of(new BigDecimal("100")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("10.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceThreshold, priceWithoutThreshold);
        return new Plan("eon", "variable", rates, Optional.empty());
    }
}
