package com.uswitch.comparator;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatePriceCommandTest {

    private CalculatePriceCommand calculatePriceCommand;

    @Before
    public void setUp() throws Exception {
        List<Plan> plan = getPlan();

        calculatePriceCommand = new CalculatePriceCommand(plan);
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
        AnnualPlanCost annualPlanCost = annualPlanCosts.get(0);

        BigDecimal bigDecimal = new BigDecimal("108.675");
        BigDecimal roundedExpectedNumber = bigDecimal.setScale(2, RoundingMode.CEILING);
        assertThat(annualPlanCost.getCost(), is(roundedExpectedNumber));


    }

    private List<Plan> getPlan() {
        PriceThreshold priceThreshold = new PriceThreshold(new BigDecimal("13.5"), Optional.of(new BigDecimal("100")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("10.0"), Optional.empty());
        List<PriceThreshold> rates = Arrays.asList(priceThreshold, priceWithoutThreshold);
        return Arrays.asList(new Plan("eon", "variable", rates, Optional.empty()));
    }
}