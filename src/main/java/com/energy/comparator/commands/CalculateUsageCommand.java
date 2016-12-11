package com.energy.comparator.commands;

import com.energy.comparator.algorithm.AnnualPlanCostCalculator;
import com.energy.comparator.domain.Plan;
import com.energy.comparator.utils.RegexHelper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.energy.comparator.utils.BigDecimalUtils.*;
import static com.energy.comparator.utils.RegexHelper.*;
import static com.energy.comparator.algorithm.VatCalculator.VAT;
import static com.energy.comparator.algorithm.VatCalculator.calculateVat;
import static java.util.regex.Pattern.compile;

public class CalculateUsageCommand implements Command {

    private static final String MONTHS_IN_YEAR = "12";
    private static final String SUPPLIER_NAME = "SUPPLIER";
    private static final String TYPE_NAME = "TYPE";
    private static final String MONTHLY_SPEND = "SPEND";

    private static final Pattern PATTERN = compile("usage" + SP
                                                    + group(SUPPLIER_NAME, SUPPLIER) + SP
                                                    + group(TYPE_NAME, RegexHelper.TYPE) + SP
                                                    + group(MONTHLY_SPEND, NUMBER));

    private AnnualPlanCostCalculator annualPlanCostCalculator;
    private List<Plan> plans;

    public CalculateUsageCommand(List<Plan> plans, AnnualPlanCostCalculator annualPlanCostCalculator) {
        this.annualPlanCostCalculator = annualPlanCostCalculator;
        this.plans = plans;
    }

    public boolean test(String line) {
        return matches(PATTERN, line);
    }

    public List<String> process(String line) {
        Matcher matcher = match(PATTERN, line);
        String monthlySpend = (matcher.group(MONTHLY_SPEND));
        String supplierName = matcher.group(SUPPLIER_NAME);

        BigDecimal annualSpend = multiply(monthlySpend, MONTHS_IN_YEAR).setScale(1);

        Optional<Plan> plan = plans.stream().filter(p -> p.getSupplier().equals(supplierName)).findFirst();

        return Collections.singletonList(String.valueOf(
                IntStream.iterate(0, i -> i + 1)
                .filter(annualEnergyConsumption -> {
                    BigDecimal vatExcludedPriceInPence = annualPlanCostCalculator.calculate(plan.get(), new BigDecimal(Integer.toString(annualEnergyConsumption)));
                    BigDecimal vatIncludedPriceInPence = add(calculateVat(vatExcludedPriceInPence, VAT), vatExcludedPriceInPence);
                    BigDecimal vatIncludedPriceInPounds = convertPenceToPounds(vatIncludedPriceInPence);
                    BigDecimal vatIncludedPriceInPoundsRounded = roundToNearestInteger(vatIncludedPriceInPounds);
                    return vatIncludedPriceInPoundsRounded.equals(annualSpend);
                })
                .findFirst()
                .getAsInt()));
    }
}
