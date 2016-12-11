package com.energy.comparator.algorithm;

import com.energy.comparator.domain.Plan;
import com.energy.comparator.domain.PriceThreshold;

import java.math.BigDecimal;
import java.util.Optional;

public class AnnualPlanCostCalculator {

    public static final String DAYS_IN_YEAR = "365";

    public BigDecimal calculate(Plan plan, BigDecimal annualEnergyConsumption){
        BigDecimal cost = BigDecimal.ZERO;
        BigDecimal remainingEnergyConsumption = annualEnergyConsumption;

        for (PriceThreshold rate : plan.getRates()) {
            Optional<BigDecimal> thresholdUnit = rate.getThresholdValue();
            if(thresholdUnit.isPresent()){
                if(remainingEnergyConsumption.compareTo(thresholdUnit.get()) == 1){
                    BigDecimal costUntilThreshold = thresholdUnit.get().multiply(rate.getPricePerKilowattHour());
                    cost = cost.add(costUntilThreshold);
                    remainingEnergyConsumption = remainingEnergyConsumption.subtract(thresholdUnit.get());
                }
            }else {
                cost = cost.add(rate.getPricePerKilowattHour().multiply(remainingEnergyConsumption));
            }
        }

        if(plan.getStandingCharge().isPresent()){
            cost = cost.add(plan.getStandingCharge().get().multiply(new BigDecimal(DAYS_IN_YEAR)));
        }
        return cost;
    }

}
