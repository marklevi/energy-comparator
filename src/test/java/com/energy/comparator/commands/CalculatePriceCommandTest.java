package com.energy.comparator.commands;

import com.energy.comparator.AnnualPlanCostCalculator;
import com.energy.comparator.Plan;
import com.energy.comparator.utils.PlanBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatePriceCommandTest {

    private CalculatePriceCommand calculatePriceCommand;

    @Before
    public void setUp() throws Exception {
        List<Plan> plans = PlanBuilder.getPlans();
        calculatePriceCommand = new CalculatePriceCommand(plans, new AnnualPlanCostCalculator());
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
    public void shouldRankPricesFromTheCheapestToTheMostExpensiveWithPrice1000() throws Exception {
        List<String> annualPlanCosts = calculatePriceCommand.process("price 1000");
        assertThat(annualPlanCosts.get(0), is("eon,variable,108.68"));
        assertThat(annualPlanCosts.get(1), is("edf,fixed,111.25"));
        assertThat(annualPlanCosts.get(2), is("ovo,standard,120.23"));
        assertThat(annualPlanCosts.get(3), is("bg,standing-charge,121.33"));
    }

//    @Test
//    public void shouldRankPricesFromTheCheapestToTheMostExpensiveWithPrice2000() throws Exception {
//        List<AnnualPlanCost> annualPlanCosts = calculatePriceCommand.process("price 2000");
//        assertThat(annualPlanCosts.get(0).getCost(), is(new BigDecimal("205.75")));
//        assertThat(annualPlanCosts.get(1).getCost(), is(new BigDecimal("213.68")));
//        assertThat(annualPlanCosts.get(2).getCost(), is(new BigDecimal("215.83")));
//        assertThat(annualPlanCosts.get(3).getCost(), is(new BigDecimal("235.73")));
//    }
}
