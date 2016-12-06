package com.energy.comparator.commands;

import com.energy.comparator.AnnualPlanCost;
import com.energy.comparator.Plan;
import com.energy.comparator.utils.PlanBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatePriceCommandTest {

    private CalculatePriceCommand calculatePriceCommand;

    @Before
    public void setUp() throws Exception {
        List<Plan> plans = PlanBuilder.getPlans();
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
}
