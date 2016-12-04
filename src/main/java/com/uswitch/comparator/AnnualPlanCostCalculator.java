package com.uswitch.comparator;

import java.math.BigDecimal;
import java.util.Optional;

public class AnnualPlanCostCalculator {

    public static final String DOLLAR_DENOMINATION = "100";
    private BigDecimal VAT = new BigDecimal("0.05");

    public AnnualPlanCost calculate(Plan plan, BigDecimal annualEnergyConsumption){

        BigDecimal vatExcludedPriceInPence = getVatExcludedPrice(plan, annualEnergyConsumption);

        BigDecimal vatAmount = vatExcludedPriceInPence.multiply(VAT);

        BigDecimal vatIncludedPriceInPence = vatExcludedPriceInPence.add(vatAmount);

        BigDecimal vatIncludedPriceInDollar = convertToDollars(vatIncludedPriceInPence);

        BigDecimal vatIncludedPriceInDollarRoundedToTwoDecimalPlaces = getPriceRoundedToTwoDecimalPlaces(vatIncludedPriceInDollar);

        return new AnnualPlanCost(plan.getSupplier(), plan.getType(), vatIncludedPriceInDollarRoundedToTwoDecimalPlaces);
    }

    private BigDecimal getPriceRoundedToTwoDecimalPlaces(BigDecimal vatIncludedPriceInDollar) {
        return vatIncludedPriceInDollar.setScale(2, BigDecimal.ROUND_CEILING);
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
        return vatIncluded.divide(new BigDecimal(DOLLAR_DENOMINATION));
    }


}
