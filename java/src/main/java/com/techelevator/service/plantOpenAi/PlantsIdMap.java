package com.techelevator.service.plantOpenAi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.Map;

public class PlantsIdMap {

    @JsonPropertyDescription("A map of plant IDs to their search words. Key: searchWord, Value: PlantId")
    @JsonProperty(required = true)
    public Map<String, Integer> plantsIdMap;
}
