package com.techelevator.dao;

import com.techelevator.model.forums.Forum;
import com.techelevator.model.forums.Thread;

import java.util.List;

public interface ForumsDao {

    List<Forum> getForums();
    List<Thread> getPostsByForum(int forumId);
    List<Thread> getThreadsByParent(int parentThreadId);
    Thread getThreadById(int id);
    Thread createPost(Thread thread, int forumId);
    Thread createThread(Thread thread, int parentId);
}
