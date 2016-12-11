package com.energy.comparator.parser;

import com.energy.comparator.domain.Plan;
import com.energy.comparator.domain.PriceThreshold;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PlanParserTest {

    @Test
    public void shouldBeAbleToParsePlansInput() throws Exception {
        PlanParser planParser = new PlanParser();
        String pathString = "plans.json";
        Path path = Paths.get(pathString);
        List<Plan> plans = planParser.parse(path);

        Plan ovoPlan = plans.stream().filter(p -> p.getSupplier().equals("ovo")).findFirst().get();
        assertThat(ovoPlan.getSupplier(), is("ovo"));
        assertThat(ovoPlan.getType(), is("standard"));
        PriceThreshold priceThreshold = new PriceThreshold(new BigDecimal("12.5"), Optional.of(new BigDecimal("300")));
        PriceThreshold priceWithoutThreshold = new PriceThreshold(new BigDecimal("11"), Optional.empty());
        assertThat(ovoPlan.getRates(), is(Arrays.asList(priceThreshold, priceWithoutThreshold)));

    }
}