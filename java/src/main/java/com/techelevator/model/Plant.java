package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Plant {
    private int id;
    @JsonProperty("common_name")
    private String name;
    private String description;
    private boolean indoor;
    @JsonProperty("scientific_name")
    private String[] scientificName;


    public Plant(int id, String name, String description, boolean indoor, String[] scientificName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.indoor = indoor;
        this.scientificName = scientificName;
    }

    public Plant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public String[] getScientificName() {
        return scientificName;
    }

    public void setScientificName(String[] scientificName) {
        this.scientificName = scientificName;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\" : " + id +
                ", \"common_name\" : \"" + name + '\"' +
                ", \"scientific_name\" : \"" + scientificName[0] +
                "\", \"description\" : \"" + description + '\"' +
                ", \"indoor\" : " + indoor +
                "}";
    }
}
