package com.energy.comparator;

import com.energy.comparator.commands.*;
import com.energy.comparator.exceptions.ExitCommandException;
import com.energy.comparator.parser.PlanParser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String [] args) {
        new Application().run(args);
    }

    private void run(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("No file received");
        } else {
            final String inputFileName = args[0];

            run(inputFileName);
        }
    }

    private void run(String inputFileName) {
        final Path inputFilePath = Paths.get(inputFileName);

        List<Plan> plans = new PlanParser().parse(inputFilePath);
        List<Command> commands = getCommands(plans);
        CommandLineProcessor commandLineProcessor = new CommandLineProcessor(commands, System.out::println);

        Scanner input = new Scanner(System.in);
        try{
            while (input.hasNextLine()) {
                String line = input.nextLine();

                String trimmedLine = line.trim();
                if(!trimmedLine.isEmpty()){
                    commandLineProcessor.process(line);
                }
            }
        }catch (ExitCommandException exitException){
            terminateApplication();
        }

    }

    private void terminateApplication() {
        System.exit(1);
    }

    private List<Command> getCommands(List<Plan> plans) {
        CalculatePriceCommand calculatePriceCommand = new CalculatePriceCommand(plans, new AnnualPlanCostCalculator());
        CalculateUsageCommand calculateUsageCommand = new CalculateUsageCommand(plans, new AnnualPlanCostCalculator());
        ExitCommand exitCommand = new ExitCommand();
        return Arrays.asList(calculatePriceCommand, calculateUsageCommand, exitCommand);
    }
}
