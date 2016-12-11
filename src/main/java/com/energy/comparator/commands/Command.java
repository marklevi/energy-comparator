package com.energy.comparator.commands;

import java.util.List;
import java.util.function.Predicate;

public interface Command extends Predicate<String> {

    boolean test(String line);

    List<String> process(String line);

}
