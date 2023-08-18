package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class UserController {
    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "/users", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User userToUpdate, Principal principal) {
        if (!principal.getName().equals(userToUpdate.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can't update other users.");
        }
        try {
            boolean userUpdated = userDao.updateUser(userToUpdate);
            if (!userUpdated) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user updated.");
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User update failed.");
        }
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        User user;
        try {
            user = userDao.getUserById(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found.");
        }
        return user;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        List<User> users;
        List<User> adjustedUsers = new ArrayList<>();
        try {
            users = userDao.getUsers();
            for (User user : users) {
                adjustedUsers.add(new User(user.getId(), user.getUsername(), user.getLocation(), user.getSkillLevel(), user.getCreated()));
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem finding users.");
        }
        return adjustedUsers;
    }
}

