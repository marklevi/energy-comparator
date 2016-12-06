package com.energy.comparator.commands;

import com.energy.comparator.AnnualPlanCost;
import com.energy.comparator.AnnualPlanCostCalculator;
import com.energy.comparator.Plan;
import com.energy.comparator.utils.BigDecimalUtils;
import com.energy.comparator.utils.RegexHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.energy.comparator.utils.RegexHelper.*;
import static java.util.regex.Pattern.compile;

public class CalculatePriceCommand implements Command {


    private static String ANNUAL_USAGE = "USAGE";
    private static final Pattern PATTERN = compile("price" + SP + group(ANNUAL_USAGE, NUMBER));
    private final AnnualPlanCostCalculator annualPlanPriceCalculator;
    private List<Plan> plans;

    public CalculatePriceCommand(List<Plan> plans) {
        this.plans = plans;
        this.annualPlanPriceCalculator = new AnnualPlanCostCalculator();
    }

    @Override
    public boolean test(String line) {
        return RegexHelper.matches(PATTERN, line);
    }

    @Override
    public List<AnnualPlanCost> process(String line) {
        final Matcher matcher = match(PATTERN, line);
        String annualUsage = matcher.group(ANNUAL_USAGE);

        List<BigDecimal> plansCosts = plans.stream()
                .map(plan -> annualPlanPriceCalculator.calculate(plan, new BigDecimal(annualUsage)))
                .map(BigDecimalUtils::applyVAT)
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
                .collect(Collectors.toList());
    }




}
