package com.example.conference.service;

import com.example.conference.dao.UserDao;
import com.example.conference.model.User;
import com.example.conference.util.SecurityUtil;
import java.sql.SQLException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User registerUser(User user, String password) throws IllegalArgumentException {
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        String hashedPassword = SecurityUtil.hashPassword(password);
        user.setPasswordHash(hashedPassword);

        try {
            userDao.save(user);
        } catch (SQLException e) {
            logger.error("Failed to register user: " + user.getEmail(), e);
            throw new IllegalStateException("Registration failed due to a database error.", e);
        }
        return user;
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOpt = userDao.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (SecurityUtil.checkPassword(password, user.getPasswordHash())) {
                logger.info("User authenticated: {}", email);
                return Optional.of(user);
            }
        }
        logger.warn("Authentication failed for email: {}", email);
        return Optional.empty();
    }
}