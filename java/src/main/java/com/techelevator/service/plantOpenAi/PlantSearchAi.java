package com.techelevator.service.plantOpenAi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PlantSearchAi {
    @JsonPropertyDescription("The plants to search for. Example: ['Daisy', 'Sunflower']")
    @JsonProperty(required = true)
    public String[] searchWords;
}
