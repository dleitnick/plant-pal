package com.techelevator.model.forums;

import java.util.List;

public class ForumBundle {

    private Forum forum;
    private List<ThreadBundle> posts;

    public ForumBundle(Forum forum, List<ThreadBundle> posts) {
        this.forum = forum;
        this.posts = posts;
    }

    public ForumBundle() {
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public List<ThreadBundle> getPosts() {
        return posts;
    }

    public void setPosts(List<ThreadBundle> posts) {
        this.posts = posts;
    }
}
