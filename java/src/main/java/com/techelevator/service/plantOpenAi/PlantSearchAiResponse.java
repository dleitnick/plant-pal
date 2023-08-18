package com.techelevator.service.plantOpenAi;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.techelevator.model.Plant;

import java.util.List;
import java.util.Map;

public class PlantSearchAiResponse {

    @JsonPropertyDescription("A map of plants. Key: searchWord, Value: List<Plant>")
    public Map<String, List<Plant>> plantsMap;

    public PlantSearchAiResponse(Map<String, List<Plant>> plants) {
        this.plantsMap = plants;
    }
}
