package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.forums.Forum;
import com.techelevator.model.forums.Thread;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcForumsDao implements ForumsDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcForumsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Forum> getForums() {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT * FROM forums;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                forums.add(mapRowtoForum(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return forums;
    }

    @Override
    public List<Thread> getPostsByForum(int forumId) {
        List<Thread> posts = new ArrayList<>();
        String sql = "SELECT t.*\n" +
                "FROM threads t\n" +
                "JOIN forums_threads ft ON t.id = ft.thread_id\n" +
                "WHERE t.is_post = true AND ft.forum_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, forumId);
            while (results.next()) {
                posts.add(mapRowToThread(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return posts;
    }

    @Override
    public List<Thread> getThreadsByParent(int parentThreadId) {
        List<Thread> threads = new ArrayList<>();
        String sql = "SELECT t.*\n" +
                "FROM thread_connections tc\n" +
                "JOIN threads t ON tc.child_thread = t.id\n" +
                "WHERE tc.parent_thread = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parentThreadId);
            while (results.next()) {
                threads.add(mapRowToThread(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return threads;
    }

    @Override
    public Thread getThreadById(int id) {
        Thread thread = new Thread();
        String sql = "SELECT * FROM threads WHERE id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                thread = mapRowToThread(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return thread;
    }

    @Override
    public Thread createPost(Thread thread, int forumId) {
        Thread post = new Thread();
        Integer postId;
        String sql =
                "WITH new_thread AS (\n" +
                "  INSERT INTO threads (user_id, date, title, message, is_post)\n" +
                "  VALUES (?, NOW(), ?, ?, true)\n" +
                "  RETURNING id\n" +
                ")\n" +
                "INSERT INTO forums_threads (forum_id, thread_id)\n" +
                "SELECT ?, new_thread.id\n" +
                "FROM new_thread\n" +
                "RETURNING thread_id;";
        try {
            postId = jdbcTemplate.queryForObject(sql, Integer.class, thread.getUserId(), thread.getTitle(), thread.getMessage(), forumId);
            if (postId != null) {
                post = getThreadById(postId);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return post;
    }

    @Override
    public Thread createThread (Thread thread, int parentId) {
        Thread newThread = new Thread();
        Integer threadId;
        String sql =
                "WITH new_thread AS (\n" +
                "  INSERT INTO threads (user_id, date, message, is_post)\n" +
                "  VALUES (?, NOW(), ?, false)\n" +
                "  RETURNING id\n" +
                ")\n" +
                "INSERT INTO thread_connections (child_thread, parent_thread)\n" +
                "SELECT new_thread.id, ?\n" +
                "FROM new_thread\n" +
                "RETURNING child_thread;";
        try {
            threadId = jdbcTemplate.queryForObject(sql, Integer.class, thread.getUserId(), thread.getMessage(), parentId);
            if (threadId != null) {
                newThread = getThreadById(threadId);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newThread;
    }


    private Forum mapRowtoForum(SqlRowSet results) {
        return new Forum(
                results.getInt("id"),
                results.getString("name"),
                results.getString("description")
        );
    }

    private Thread mapRowToThread(SqlRowSet results) {
        Thread thread = new Thread();
        thread.setId(results.getInt("id"));
        thread.setUserId(results.getInt("user_id"));
        thread.setDate(Objects.requireNonNull(results.getTimestamp("date")).toLocalDateTime());
        if (results.getString("title") != null) {
            thread.setTitle(results.getString("title"));
            thread.setUrlSlug();
        }
        thread.setMessage(results.getString("message"));
        thread.setUpVotes(results.getInt("upvotes"));
        thread.setDownVotes(results.getInt("downvotes"));
        thread.setDeleteFlag(results.getBoolean("delete_flag"));
        thread.setPost(results.getBoolean("is_post"));
        return thread;
    }
}
