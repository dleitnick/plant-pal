package com.techelevator.dao;

import com.techelevator.model.Review;
import com.techelevator.exception.DaoException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReviewDao implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Review getReview(int id) {
       Review review = null;
       String sql = "SELECT * FROM plant_reviews WHERE review_id = ?";
       try {
           SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
           if(results.next()){
               review = mapRowToReview(results);
           }else throw new DaoException("No review with this ID: " + id);
       } catch (CannotGetJdbcConnectionException e) {
           throw new DaoException("Unable to connect to server or database", e);
       } catch (DataIntegrityViolationException e) {
           throw new DaoException("Data integrity violation", e);
       }
        return review;
    }

    @Override
    public List<Review> getReviewsByPlant(int id) {
        List<Review> review = new ArrayList<>();
        String sql = "SELECT * FROM plant_reviews WHERE plant_id = ? ORDER BY date_time DESC";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
                    throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
                    throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    @Override
    public int postReview(Review review) {
        Integer newReviewId;
        String sql = "INSERT INTO plant_reviews" +
                " (plant_id, review_text, date_time, location, username, rating)" +
                " VALUES (?, ?, Now(), ?, ?, ?)" +
                " RETURNING review_id;";
        try {
            newReviewId = jdbcTemplate.queryForObject(sql, Integer.class,
                    review.getPlantId(), review.getReviewText(), review.getLocation(), review.getUsername(), review.getRating());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        if (newReviewId == null) {
            throw new DaoException("No review posted.");
        }
        return newReviewId;
    }


    private Review mapRowToReview(SqlRowSet results){
        Review review = new Review();
        review.setReviewId(results.getInt("review_id"));
        review.setReviewText(results.getString("review_text"));
        Timestamp ts = results.getTimestamp("date_time");
        if(ts != null) {
            review.setDateTime(ts.toLocalDateTime());
        }
        review.setLocation(results.getString("location"));
        review.setUsername(results.getString("username"));
        review.setRating(results.getInt("rating"));
        review.setPlantId(results.getInt("plant_id"));
        return review;
    }
}