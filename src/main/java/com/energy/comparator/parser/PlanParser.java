package com.energy.comparator.parser;

import com.energy.comparator.Plan;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class PlanParser {
    public List<Plan> parse(Path inputFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream;
        try {
            inputStream = Files.newInputStream(inputFilePath);
        } catch (IOException e) {
            throw new RuntimeException("unable to parse input file");
        }

        List<PlanDTO> planDTOs = null;

        try {
            planDTOs = mapper
                    .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                    .readValue(inputStream, new TypeReference<List<PlanDTO>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("unable to deserialize object");
        }

        return planDTOs.stream().map(PlanDTO::build).collect(Collectors.toList());
    }
}
