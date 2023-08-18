package com.techelevator.model;

import java.time.LocalDateTime;

public class Comment {

    private int commentId;
    private String commentText;
    private LocalDateTime dateTime;
    private String location;
    private String username;
    private int plantId;

    // Constructors, getters, setters, and other methods would follow

    public Comment() {}

    public Comment(int commentId, String commentText, LocalDateTime dateTime, String location, String username, int plantId) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.dateTime = dateTime;
        this.location = location;
        this.username = username;
        this.plantId = plantId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public int getPlantId(){
        return plantId;

    }
    public void setPlantId(int plantId){
        this.plantId = plantId;
    }
}

