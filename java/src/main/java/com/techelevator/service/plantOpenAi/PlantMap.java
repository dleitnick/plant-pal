package com.techelevator.service.plantOpenAi;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.techelevator.model.Plant;

import java.util.List;
import java.util.Map;

public class PlantMap {

    @JsonPropertyDescription("A map of plants to their search words. Key: searchWord, Value: Plant")
    public Map<String, Plant> plantsMap;

    public PlantMap(Map<String, Plant> plantsMap) {
        this.plantsMap = plantsMap;
    }
}
