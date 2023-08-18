package com.techelevator.dao;

import com.techelevator.model.Comment;

import java.util.List;

public interface CommentDao {

    Comment getComment(int id);
    List<Comment> getCommentsByPlant(int id);
    int postComment(Comment comment);
}
