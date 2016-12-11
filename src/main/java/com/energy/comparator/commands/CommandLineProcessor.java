package com.energy.comparator.commands;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CommandLineProcessor {

    private final List<Command> commands;
    private final Consumer<String> outputHandler;

    public CommandLineProcessor(List<Command> commands, Consumer<String> outputHandler) {
        this.commands = commands;
        this.outputHandler = outputHandler;
    }

    public void process(String line){
        commands.stream()
                .filter(c -> c.test(line))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Input unrecognized: " + line))
                .process(line)
                .forEach(outputHandler);
    }
}
