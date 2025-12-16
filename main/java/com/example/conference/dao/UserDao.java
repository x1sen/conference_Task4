package com.example.conference.dao;

import com.example.conference.model.User;
import java.sql.*;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private static final String FIND_BY_EMAIL = "SELECT id, email, password_hash, first_name, last_name, role FROM users WHERE email = ?";
    private static final String INSERT_USER = "INSERT INTO users (email, password_hash, first_name, last_name, role) VALUES (?, ?, ?, ?, ?)";

    public Optional<User> findByEmail(String email) {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_BY_EMAIL);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapUser(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding user by email: " + email, e);
        } finally {
            close(conn);
        }
        return Optional.empty();
    }

    public void save(User user) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getRole().name());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error saving user: " + user.getEmail(), e);
            throw e;
        } finally {
            close(conn);
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .passwordHash(rs.getString("password_hash"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .role(User.Role.valueOf(rs.getString("role")))
                .build();
    }
}