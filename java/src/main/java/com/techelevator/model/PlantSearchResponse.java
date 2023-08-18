package com.techelevator.model;

import java.util.List;

public class PlantSearchResponse {

    private List<Plant> data;

    public PlantSearchResponse(List<Plant> data) {
        this.data = data;
    }

    public PlantSearchResponse() {
    }

    public List<Plant> getData() {
        return data;
    }

    public void setData(List<Plant> data) {
        this.data = data;
    }
}
