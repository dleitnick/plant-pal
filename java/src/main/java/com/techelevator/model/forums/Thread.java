package com.techelevator.model.forums;

import com.github.slugify.Slugify;
import com.techelevator.model.User;

import java.time.LocalDateTime;

public class Thread {
    private int id;
    private int userId;
    private User user;
    private LocalDateTime date;
    private String title;
    private String message;
    private int upVotes;
    private int downVotes;
    private boolean deleteFlag;
    private boolean isPost;
    private String urlSlug;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
    }

    public String getUrlSlug() {
        return urlSlug;
    }

    public void setUrlSlug() {
        Slugify slg = Slugify.builder().build();
        this.urlSlug = slg.slugify(title);
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", upVotes=" + upVotes +
                ", downVotes=" + downVotes +
                ", deleteFlag=" + deleteFlag +
                ", isPost=" + isPost +
                '}';
    }
}
