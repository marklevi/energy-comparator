package com.energy.comparator.algorithm;

import com.energy.comparator.domain.Plan;
import com.energy.comparator.domain.PriceThreshold;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Optional;

import static com.energy.comparator.utils.BigDecimalUtils.add;
import static com.energy.comparator.utils.BigDecimalUtils.multiply;
import static com.energy.comparator.utils.BigDecimalUtils.subtract;

public class AnnualPlanCostCalculator {

    private static final String DAYS_IN_YEAR = "365";


    public BigDecimal calculate(Plan plan, BigDecimal annualEnergyConsumption) {
        BigDecimal cost = getPreStandingChargeCostRecursively(plan, plan.getRate(), annualEnergyConsumption, BigDecimal.ZERO);

        if (plan.getStandingCharge().isPresent()) {
            return add(cost,  multiply(plan.getStandingCharge().orElse(BigDecimal.ZERO), new BigDecimal(DAYS_IN_YEAR)));
        }

        return cost;
    }

    private BigDecimal getPreStandingChargeCostRecursively(Plan plan, Iterator<PriceThreshold> iterator, BigDecimal remainingEnergyConsumption, BigDecimal cost) {
        if (remainingEnergyConsumption.equals(BigDecimal.ZERO)) {
            return cost;
        }

        if (iterator.hasNext()) {
            PriceThreshold rate = iterator.next();
            Optional<BigDecimal> thresholdValue = rate.getThresholdValue();
            if (thresholdValue.isPresent() && remainingEnergyConsumption.compareTo(thresholdValue.get()) == 1) {
                BigDecimal addedCost = multiply(rate.getPricePerKilowattHour(), thresholdValue.get());
                return getPreStandingChargeCostRecursively(plan, iterator, subtract(remainingEnergyConsumption, thresholdValue.get()), add(cost, addedCost));
            } else {
                BigDecimal addedCost = multiply(rate.getPricePerKilowattHour(), remainingEnergyConsumption);
                return getPreStandingChargeCostRecursively(plan, iterator, BigDecimal.ZERO, add(cost, addedCost));
            }
        }
        return cost;
    }
}
