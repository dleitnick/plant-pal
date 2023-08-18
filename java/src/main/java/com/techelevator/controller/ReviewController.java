package com.techelevator.controller;

import com.techelevator.dao.ReviewDao;
import com.techelevator.model.Review;
import com.techelevator.exception.DaoException;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class ReviewController {

    private ReviewDao reviewDao;

    public ReviewController(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @RequestMapping(path = "/reviews/{id}", method = RequestMethod.GET)
    public Review getReviewById(@PathVariable int id) {
        Review review;
        try {
            review = reviewDao.getReview(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No review found");
        }
        return review;
    }

    @RequestMapping(path = "/plants/{id}/reviews", method = RequestMethod.GET)
    public List<Review> getReviewsByPlantId(@PathVariable int id){
        List<Review> reviewList = new ArrayList<>();
        try{
            reviewList = reviewDao.getReviewsByPlant(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found");
        }
        return reviewList;
    }

    @RequestMapping(path = "/reviews", method = RequestMethod.POST)
    public int addReview(@RequestBody Review review, Principal principal){
        if (!verifyReviewBelongsToUser(review, principal)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot add review for another user.");
        }
        int reviewId;
        try{
            reviewId = reviewDao.postReview(review);
        }catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add review!");
        }
        return reviewId;
    }

    public boolean verifyReviewBelongsToUser(Review review, Principal principal) {
        if (principal == null) return false;
        return review.getUsername().equals(principal.getName());
    }
}






