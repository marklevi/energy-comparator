package com.energy.comparator.commands;

import com.energy.comparator.domain.AnnualPlanCost;
import com.energy.comparator.algorithm.AnnualPlanCostCalculator;
import com.energy.comparator.domain.Plan;
import com.energy.comparator.utils.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.energy.comparator.utils.BigDecimalUtils.add;
import static com.energy.comparator.utils.RegexHelper.*;
import static com.energy.comparator.utils.VatCalculator.VAT;
import static com.energy.comparator.utils.VatCalculator.calculateVat;
import static java.util.regex.Pattern.compile;

public class CalculatePriceCommand implements Command {

    private static String ANNUAL_USAGE = "USAGE";
    private static final Pattern PATTERN = compile("price" + SP + group(ANNUAL_USAGE, NUMBER));
    private final AnnualPlanCostCalculator annualPlanPriceCalculator;
    private List<Plan> plans;

    public CalculatePriceCommand(List<Plan> plans, AnnualPlanCostCalculator annualPlanCostCalculator) {
        this.plans = plans;
        this.annualPlanPriceCalculator = annualPlanCostCalculator;
    }

    @Override
    public boolean test(String line) {
        return matches(PATTERN, line);
    }

    @Override
    public List<String> process(String line) {
        final Matcher matcher = match(PATTERN, line);
        String annualUsage = matcher.group(ANNUAL_USAGE);

        List<BigDecimal> plansCosts = plans.stream()
                .map(plan -> annualPlanPriceCalculator.calculate(plan, new BigDecimal(annualUsage)))
                .map(vatExcludedPriceInPence -> add(calculateVat(vatExcludedPriceInPence, VAT), vatExcludedPriceInPence))
                .map(BigDecimalUtils::convertPenceToPounds)
                .map(BigDecimalUtils::roundByTwoDecimalPlaces)
                .collect(Collectors.toList());

        return IntStream.range(0, plans.size())
                .mapToObj(index -> {
                    Plan plan = plans.get(index);
                    BigDecimal planCost = plansCosts.get(index);
                    return new AnnualPlanCost(plan.getSupplier(), plan.getType(), planCost);
                })
                .sorted()
                .map(AnnualPlanCost::toString)
                .collect(Collectors.toList());
    }

}
