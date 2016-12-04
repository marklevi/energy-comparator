package com.uswitch.comparator;

import java.util.List;
import java.util.function.Predicate;

public interface Command extends Predicate<String> {

    @Override
    boolean test(String line);

    List<AnnualPlanCost> process(String line);

}
