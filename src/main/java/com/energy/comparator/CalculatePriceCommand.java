package com.energy.comparator;

import com.energy.comparator.utils.RegexHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.regex.Pattern.compile;

public class CalculatePriceCommand implements Command {
    private BigDecimal VAT = new BigDecimal("0.05");

    private static String PRICE = "PRICE";
    private static final Pattern PATTERN = compile("price" + RegexHelper.SP + RegexHelper.group(PRICE, RegexHelper.NUMBER));
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
        final Matcher matcher = RegexHelper.match(PATTERN, line);
        String price = matcher.group(PRICE);

        List<BigDecimal> plansCosts = plans.stream()
                .map(plan -> annualPlanPriceCalculator.calculate(plan, new BigDecimal(price)))
                .map(vatExcludedPriceInPence -> applyVAT(vatExcludedPriceInPence, VAT))
                .map(this::convertToDollars)
                .map(this::roundByTwoDecimalPlaces)
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

    private BigDecimal roundByTwoDecimalPlaces(BigDecimal vatIncludedPriceInDollar) {
        return vatIncludedPriceInDollar.setScale(2, BigDecimal.ROUND_CEILING);
    }

    private BigDecimal convertToDollars(BigDecimal vatIncludedPriceInPence) {
        return vatIncludedPriceInPence.divide(new BigDecimal("100"));
    }

    private BigDecimal applyVAT(BigDecimal vatExcludedPriceInPence, BigDecimal VAT) {
        BigDecimal vatAmount = vatExcludedPriceInPence.multiply(VAT);
        return vatExcludedPriceInPence.add(vatAmount);
    }

}
