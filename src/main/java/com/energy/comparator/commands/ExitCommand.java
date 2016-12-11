package com.energy.comparator.commands;

import com.energy.comparator.exceptions.ExitCommandException;

import java.util.List;
import java.util.regex.Pattern;

import static com.energy.comparator.utils.RegexHelper.*;
import static java.util.regex.Pattern.compile;

public class ExitCommand implements Command {

    private static final Pattern PATTERN = compile("exit");

    @Override
    public boolean test(String line) {
        return matches(PATTERN, line);
    }

    @Override
    public List<String> process(String line) {
        throw new ExitCommandException("Exit command issued, will terminate program");
    }
}
