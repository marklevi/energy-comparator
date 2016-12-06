package com.energy.comparator.commands;

import com.energy.comparator.AnnualPlanCost;
import com.energy.comparator.AnnualPlanCostCalculator;
import com.energy.comparator.Plan;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.energy.comparator.utils.PlanBuilder.getPlans;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CalculateUsageCommandTest {

    @Test
    public void findEnergyConsumptionBasedOnMonthlySpend() throws Exception {
        List<Plan> plans = getPlans();
        CalculateUsageCommand calculateUsageCommand = new CalculateUsageCommand(new AnnualPlanCostCalculator(), plans);

        BigDecimal annualEnergyConsumption = calculateUsageCommand.process("usage ovo standard 1000");

        assertThat(annualEnergyConsumption, is(new BigDecimal("103855")));

    }
}