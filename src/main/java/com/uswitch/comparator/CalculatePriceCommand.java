package com.uswitch.comparator;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.uswitch.comparator.utils.RegexHelper.*;
import static java.util.regex.Pattern.compile;

public class CalculatePriceCommand implements Command {

    private static String PRICE = "PRICE";
    private static final Pattern PATTERN = compile("price" + SP + group(PRICE, NUMBER));
    private final AnnualPlanCostCalculator annualPlanPriceCalculator;
    private List<Plan> plan;

    public CalculatePriceCommand(List<Plan> plan) {
        this.plan = plan;
        this.annualPlanPriceCalculator = new AnnualPlanCostCalculator();
    }

    @Override
    public boolean test(String line) {
        return matches(PATTERN, line);
    }

    @Override
    public List<AnnualPlanCost> process(String line) {
        final Matcher matcher = match(PATTERN, line);
        String price = matcher.group(PRICE);

        return plan.stream()
                .map(plan -> annualPlanPriceCalculator.calculate(plan, new BigDecimal(price)))
                .collect(Collectors.toList());
    }

}
