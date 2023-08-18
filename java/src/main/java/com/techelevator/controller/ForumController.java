package com.techelevator.controller;

import com.techelevator.dao.ForumsDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.forums.Forum;
import com.techelevator.model.forums.ForumBundle;
import com.techelevator.model.forums.Thread;
import com.techelevator.model.forums.ThreadBundle;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ForumController {

    private final ForumsDao forumsDao;
    private final UserDao userDao;

    public ForumController(ForumsDao forumsDao, UserDao userDao) {
        this.forumsDao = forumsDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/forums", method = RequestMethod.GET)
    public List<Forum> getForums() {
        List<Forum> forums;
        try {
            forums = forumsDao.getForums();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No forums found");
        }
        return forums;
    }

    @RequestMapping(path = "/forums/{id}", method = RequestMethod.GET)
    public List<Thread> getPostsByForum(@PathVariable int id) {
        List<Thread> posts;
        try {
            posts = forumsDao.getPostsByForum(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No forums found");
        }
        for (Thread post : posts) {
            try {
                post.setUser(userDao.getUserById(post.getUserId()));
            } catch (DaoException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found for that post");
            }
        }
        return posts;

    }

    @RequestMapping(path = "/threads/{id}", method = RequestMethod.GET)
    public List<Thread> getThreadsByParent(@PathVariable int id) {
        List<Thread> threads;
        try {
            threads = forumsDao.getThreadsByParent(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No threads found");
        }
        for (Thread thread : threads) {
            try {
                thread.setUser(userDao.getUserById(thread.getUserId()));
            } catch (DaoException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found for that thread");
            }
        }
        return threads;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/forums/{id}", method = RequestMethod.POST)
    public Thread createPost(@PathVariable int id, @RequestBody Thread thread) {
        Thread newPost = new Thread();
        try {
            newPost = forumsDao.createPost(thread, id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't create post" + e.getMessage());
        }
        try {
            newPost.setUser(userDao.getUserById(newPost.getUserId()));
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found for that post");
        }
        return newPost;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/threads/{id}", method = RequestMethod.POST)
    public Thread createThread(@PathVariable int id, @RequestBody Thread thread) {
        Thread newThread = new Thread();
        try {
            newThread = forumsDao.createThread(thread, id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't create thread" + e.getMessage());
        }
        try {
            newThread.setUser(userDao.getUserById(newThread.getUserId()));
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found for that thread");
        }
        return newThread;
    }

    // Only using for project demonstration
    @RequestMapping(path = "/forums/all", method = RequestMethod.GET)
    public List<ForumBundle> getAllForums() {
        List<ForumBundle> forumBundleList = new ArrayList<>();
        List<Forum> forums;
        try {
            forums = forumsDao.getForums();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No forums found");
        }
        for (Forum forum : forums) {
            ForumBundle forumBundle = new ForumBundle();
            forumBundle.setForum(forum);
            forumBundleList.add(forumBundle);
        }
        for (ForumBundle forumBundle : forumBundleList) {
            List<ThreadBundle> threadBundleList = new ArrayList<>();
            List<Thread> posts;
            try {
                posts = forumsDao.getPostsByForum(forumBundle.getForum().getId());
            } catch (DaoException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No forums found");
            }
            for (Thread post : posts) {
                try {
                    post.setUser(userDao.getUserById(post.getUserId()));
                } catch (DaoException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found for that post");
                }
                threadBundleList.add(bundleThread(post));
            }
            forumBundle.setPosts(threadBundleList);
        }
        return forumBundleList;
    }

    private ThreadBundle bundleThread(Thread threadToBundle) {
        List<Thread> threads = new ArrayList<>();
        try {
            threads = forumsDao.getThreadsByParent(threadToBundle.getId());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No threads found");
        }
        List<ThreadBundle> threadBundleList = new ArrayList<>();
        for (Thread thread : threads) {
            try {
                thread.setUser(userDao.getUserById(thread.getUserId()));
            } catch (DaoException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found for that thread");
            }
            threadBundleList.add(bundleThread(thread));
        }
        ThreadBundle threadBundleToReturn = new ThreadBundle();
        threadBundleToReturn.setThread(threadToBundle);
        threadBundleToReturn.setChildThreads(threadBundleList);
        return threadBundleToReturn;
    }
}
