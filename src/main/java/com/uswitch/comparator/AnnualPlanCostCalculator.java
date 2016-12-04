package com.uswitch.comparator;

import java.math.BigDecimal;
import java.util.Optional;

public class AnnualPlanCostCalculator {

    private BigDecimal VAT = new BigDecimal("0.05");

    public AnnualPlanCost calculate(Plan plan, BigDecimal annualEnergyConsumption){
        BigDecimal vatExcludedPrice = getVatExcludedPrice(plan, annualEnergyConsumption);
        BigDecimal vatIncluded = getVATIncluded(vatExcludedPrice);
        BigDecimal dollarCost = convertToDollars(vatIncluded);
        BigDecimal totalCostToTwoDecimalPlaces = dollarCost.setScale(2, BigDecimal.ROUND_CEILING);


        return new AnnualPlanCost(plan.getSupplier(), plan.getType(), totalCostToTwoDecimalPlaces);
    }

    private BigDecimal getVatExcludedPrice(Plan plan, BigDecimal annualEnergyConsumption) {
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
        return cost;
    }

    private BigDecimal convertToDollars(BigDecimal vatIncluded) {
        return vatIncluded.divide(new BigDecimal("100"));
    }

    private BigDecimal getVATIncluded(BigDecimal cost) {
        BigDecimal addedVat = BigDecimal.ONE.add(VAT);
        return cost.multiply(addedVat);
    }


}
