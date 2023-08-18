package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlantImages {
    @JsonProperty("original_url")
    String originalUrl;
    @JsonProperty("regular_url")
    String regularUrl;
    @JsonProperty("medium_url")
    String mediumUrl;
    @JsonProperty("small_url")
    String smallUrl;
    String thumbnail;

    public PlantImages(String originalUrl, String regularUrl, String mediumUrl, String smallUrl, String thumbnail) {
        this.originalUrl = originalUrl;
        this.regularUrl = regularUrl;
        this.mediumUrl = mediumUrl;
        this.smallUrl = smallUrl;
        this.thumbnail = thumbnail;
    }

    public PlantImages() {
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getRegularUrl() {
        return regularUrl;
    }

    public void setRegularUrl(String regularUrl) {
        this.regularUrl = regularUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
