package com.example.conference.dao;

import com.example.conference.util.ConnectionPool;
import com.example.conference.util.SecurityUtil;
import com.example.conference.model.User;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);
    protected final ConnectionPool pool = ConnectionPool.getInstance();

    protected Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    protected void close(Connection connection) {
        pool.releaseConnection(connection);
    }

    public void initDatabase() {
        try (Connection conn = getConnection()) {

            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "email VARCHAR(255) UNIQUE NOT NULL," +
                    "password_hash VARCHAR(255) NOT NULL," +
                    "first_name VARCHAR(255)," +
                    "last_name VARCHAR(255)," +
                    "role VARCHAR(50) NOT NULL" +
                    ");";

            String createConferencesTable = "CREATE TABLE IF NOT EXISTS conferences (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "description CLOB," +
                    "date TIMESTAMP," +
                    "location VARCHAR(255)" +
                    ");";

            String createSectionsTable = "CREATE TABLE IF NOT EXISTS sections (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "conference_id BIGINT NOT NULL," +
                    "name VARCHAR(255) NOT NULL," +
                    "description CLOB," +
                    "FOREIGN KEY (conference_id) REFERENCES conferences(id)" +
                    ");";

            String createApplicationsTable = "CREATE TABLE IF NOT EXISTS applications (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id BIGINT NOT NULL," +
                    "section_id BIGINT NOT NULL," +
                    "topic VARCHAR(512)," +
                    "status VARCHAR(50) NOT NULL," +
                    "question CLOB," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (section_id) REFERENCES sections(id)" +
                    ");";

            conn.createStatement().execute(createUsersTable);
            conn.createStatement().execute(createConferencesTable);
            conn.createStatement().execute(createSectionsTable);
            conn.createStatement().execute(createApplicationsTable);


            String adminEmail = "admin@conf.com";
            UserDao userDao = new UserDao();
            if (userDao.findByEmail(adminEmail).isEmpty()) {
                User admin = User.builder()
                        .email(adminEmail)
                        .passwordHash(SecurityUtil.hashPassword("admin_pass"))
                        .firstName("Super")
                        .lastName("Admin")
                        .role(User.Role.ADMIN)
                        .build();
                userDao.save(admin);
                logger.info("Default Admin user created.");
            }

            logger.info("Database tables initialized.");

        } catch (SQLException e) {
            logger.error("Error during database initialization.", e);
            throw new RuntimeException("Database initialization failed.", e);
        }
    }
}