package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Comment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCommentDao implements CommentDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Comment getComment(int id) {
        Comment comment;
        String sql = "SELECT * FROM comments WHERE comment_id = ? ORDER BY date_time DESC;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                comment = mapRowtoComment(results);
            } else throw new DaoException("No comment with ID: " + id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return comment;
    }

    @Override
    public List<Comment> getCommentsByPlant(int id) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE plant_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                comments.add(mapRowtoComment(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return comments;
    }

    @Override
    public int postComment(Comment comment) {
        Integer newCommentId;
        String sql = "INSERT INTO comments" +
                " (plant_id, comment_text, date_time, location, username)" +
                " VALUES (?, ?, Now(), ?, ?)" +
                " RETURNING comment_id;";
        try {
            newCommentId = jdbcTemplate.queryForObject(sql, Integer.class,
                    comment.getPlantId(), comment.getCommentText(), comment.getLocation(), comment.getUsername());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        if (newCommentId == null) {
            throw new DaoException("No comment posted.");
        }
        return newCommentId;
    }

    private Comment mapRowtoComment(SqlRowSet results) {
        Comment comment = new Comment();
        comment.setCommentId(results.getInt("comment_id"));
        comment.setPlantId(results.getInt("plant_id"));
        comment.setCommentText(results.getString("comment_text"));
        Timestamp ts = results.getTimestamp("date_time");
        if(ts != null) {
            comment.setDateTime(ts.toLocalDateTime());
        }
        comment.setLocation(results.getString("location"));
        comment.setUsername(results.getString("username"));
        return comment;
    }
}
