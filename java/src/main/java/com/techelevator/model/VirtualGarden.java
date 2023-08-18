package com.techelevator.model;


public class VirtualGarden {

    private int userId;
    private int plantId;

    public VirtualGarden(int userId, int plantId) {
        this.userId = userId;
        this.plantId = plantId;
    }

    public VirtualGarden() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }
}
