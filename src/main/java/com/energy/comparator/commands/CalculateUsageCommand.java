package com.energy.comparator.commands;

import com.energy.comparator.AnnualPlanCostCalculator;
import com.energy.comparator.Plan;
import com.energy.comparator.utils.RegexHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.energy.comparator.utils.BigDecimalUtils.*;
import static com.energy.comparator.utils.RegexHelper.*;
import static java.util.regex.Pattern.compile;

public class CalculateUsageCommand {

    private static final String SUPPLIER_NAME = "SUPPLIER";
    private static final String TYPE_NAME = "TYPE";
    private static final String MONTHLY_SPEND = "SPEND";

    private static final Pattern PATTERN = compile("usage" + SP
                                                    + group(SUPPLIER_NAME, SUPPLIER) + SP
                                                    + group(TYPE_NAME, RegexHelper.TYPE) + SP
                                                    + group(MONTHLY_SPEND, NUMBER));

    private AnnualPlanCostCalculator annualPlanCostCalculator;
    private List<Plan> plans;

    public CalculateUsageCommand(AnnualPlanCostCalculator annualPlanCostCalculator, List<Plan> plans) {
        this.annualPlanCostCalculator = annualPlanCostCalculator;
        this.plans = plans;
    }

    public boolean test(String line) {
        return matches(PATTERN, line);
    }

    public BigDecimal process(String line) {
        Matcher matcher = match(PATTERN, line);
        String monthlySpend = matcher.group(MONTHLY_SPEND);
        String supplierName = matcher.group(SUPPLIER_NAME);

        BigDecimal annualSpend = new BigDecimal(monthlySpend).multiply(new BigDecimal("12"));

        Optional<Plan> plan = plans.stream().filter(p -> p.getSupplier().equals(supplierName)).findFirst();

        return BigDecimal.valueOf(IntStream.iterate(0, i -> i + 1)
                .filter(annualEnergyConsumption -> {
                    BigDecimal vatExcludedPriceInPence = annualPlanCostCalculator.calculate(plan.get(), new BigDecimal(Integer.toString(annualEnergyConsumption)));
                    BigDecimal vatIncludedPriceInPence = applyVAT(vatExcludedPriceInPence);
                    BigDecimal pounds = convertPenceToPounds(vatIncludedPriceInPence);
                    BigDecimal roundedPound = roundToNearestInteger(pounds);
                    return roundedPound.equals(annualSpend);
                })
                .findFirst().getAsInt());
    }



}
