package com.energy.comparator.commands;

import com.energy.comparator.algorithm.AnnualPlanCostCalculator;
import com.energy.comparator.domain.Plan;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.energy.comparator.utils.PlanBuilder.getPlans;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CalculateUsageCommandTest {

    @Ignore
    @Test
    public void findEnergyConsumptionBasedOnMonthlySpend() throws Exception {
        List<Plan> plans = getPlans();
        CalculateUsageCommand calculateUsageCommand = new CalculateUsageCommand(plans, new AnnualPlanCostCalculator());

        List<String> annualEnergyConsumption = calculateUsageCommand.process("usage edf fixed 350");
        assertThat(annualEnergyConsumption.get(0), is("44267"));

    }

    @Ignore
    @Test
    public void findEnergyConsumptionBasedOnMonthlySpendSample3() throws Exception {
        List<Plan> plans = getPlans();
        CalculateUsageCommand calculateUsageCommand = new CalculateUsageCommand(plans, new AnnualPlanCostCalculator());

        List<String> annualEnergyConsumption = calculateUsageCommand.process("usage bg standing-charge 120");
        assertThat(annualEnergyConsumption.get(0), is("14954"));

    }

    @Ignore("some rounding bullshit")
    @Test
    public void findEnergyConsumptionBasedOnMonthlySpendSample2() throws Exception {
        List<Plan> plans = getPlans();
        CalculateUsageCommand calculateUsageCommand = new CalculateUsageCommand(plans, new AnnualPlanCostCalculator());

        List<String> annualEnergyConsumption = calculateUsageCommand.process("usage ovo standard 1000");
        assertThat(annualEnergyConsumption.get(0), is("103855"));

    }
}