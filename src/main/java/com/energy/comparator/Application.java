package com.energy.comparator;

import com.energy.comparator.algorithm.AnnualPlanCostCalculator;
import com.energy.comparator.commands.*;
import com.energy.comparator.domain.Plan;
import com.energy.comparator.exceptions.ExitCommandException;
import com.energy.comparator.parser.PlanParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Application {

    private static final String OUTPUT_TXT = "output.txt";

    public static void main(String [] args) throws IOException {
        new Application().run(args);
    }

    private void run(String[] args) throws IOException {
        if (args.length < 1) {
            throw new RuntimeException("Missing arguments");
        }
        String inputFileName = args[0];
        run(inputFileName);
    }

    private void run(String inputFileName) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_TXT))){
            CommandLineProcessor commandLineProcessor = new CommandLineProcessor(getCommands(inputFileName), getOutputHandler(writer));

            Scanner input = new Scanner(System.in);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String trimmedLine = line.trim();
                if(!trimmedLine.isEmpty()){
                    commandLineProcessor.process(line);
                }
            }
        }catch (IOException e){
            throw new RuntimeException("unable to open output file");
        }catch (ExitCommandException exitException){
            terminateApplication();
        }
    }

    private Consumer<String> getOutputHandler(BufferedWriter w) {
        return output -> {
            try {
                w.write(output);
                w.newLine();
            } catch (IOException e) {
                throw new RuntimeException("unable to write to output text");
            }
        };
    }

    private List<Command> getCommands(String inputFileName) {
        Path inputFilePath = Paths.get(inputFileName);
        List<Plan> plans = new PlanParser().parse(inputFilePath);
        return getSupportedCommands(plans);
    }

    private List<Command> getSupportedCommands(List<Plan> plans) {
        return Arrays.asList(
                new CalculatePriceCommand(plans, new AnnualPlanCostCalculator()),
                new CalculateUsageCommand(plans, new AnnualPlanCostCalculator()),
                new ExitCommand());
    }

    private void terminateApplication() {
        System.exit(1);
    }
}
