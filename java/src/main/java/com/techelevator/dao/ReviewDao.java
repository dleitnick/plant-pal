package com.techelevator.dao;

import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {

    Review getReview(int id);
    List<Review> getReviewsByPlant(int id);
    int postReview(Review review);
}
