package com.techelevator.controller;

import com.techelevator.dao.CommentDao;
import com.techelevator.model.Comment;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Review;
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
public class CommentController {

    private CommentDao commentDao;

    public CommentController(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @RequestMapping(path = "/comments/{id}", method = RequestMethod.GET)
    public Comment getCommentById(@PathVariable int id) {
        Comment comment;
        try {
            comment = commentDao.getComment(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No comment found");
        }
        return comment;
    }

    @RequestMapping(path = "/plants/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getCommentsByPlantId(@PathVariable int id) {
        List<Comment> comments = new ArrayList<>();
        try {
            comments = commentDao.getCommentsByPlant(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No comments found");
        }
        return comments;
    }

    @RequestMapping(path = "/comments", method = RequestMethod.POST)
    public int addComment(@RequestBody Comment comment, Principal principal){
        if (!verifyReviewBelongsToUser(comment, principal)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot add comment for another user.");
        }
        int commentId;
        try{
            commentId = commentDao.postComment(comment);
        }catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return commentId;
    }

    public boolean verifyReviewBelongsToUser(Comment comment, Principal principal) {
        return comment.getUsername().equals(principal.getName());
    }
}
