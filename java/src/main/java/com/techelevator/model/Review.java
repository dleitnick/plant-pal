package com.techelevator.model;

import java.time.LocalDateTime;

public class Review {

    private int reviewId;
    private String reviewText;
    private LocalDateTime dateTime;
    private String location;
    private String username;
    private int rating;
    private int plantId;

    public Review() {}

    public Review(int reviewId, String reviewText, LocalDateTime dateTime, String location, String username, int rating, int plantId) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.dateTime = dateTime;
        this.location = location;
        this.username = username;
        this.rating = rating;
        this.plantId = plantId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPlantId(){
        return plantId;
    }
    public void setPlantId(int plantId){
        this.plantId = plantId;
    }
}

