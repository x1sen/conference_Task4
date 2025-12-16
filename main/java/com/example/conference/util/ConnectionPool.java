package com.example.conference.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String DB_URL = "jdbc:h2:mem:conferencedb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private static final int POOL_SIZE = 10;

    private static volatile ConnectionPool instance;

    private BlockingQueue<Connection> connectionQueue;

    private ConnectionPool() {
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        try {
            Class.forName("org.h2.Driver");

            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                connectionQueue.put(connection);
            }
            logger.info("ConnectionPool initialized with {} connections.", POOL_SIZE);
        } catch (SQLException | InterruptedException | ClassNotFoundException e) {
            logger.error("Error initializing connection pool.", e);
            throw new RuntimeException("Cannot initialize connection pool.", e);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection connection = connectionQueue.poll(5, TimeUnit.SECONDS);
            if (connection == null) {
                logger.error("Connection pool is exhausted after 5 seconds wait.");
                throw new SQLException("Connection pool is exhausted.");
            }
            return connection;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for connection.", e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Error releasing connection.", e);
            } catch (SQLException e) {
                logger.error("Error resetting connection state.", e);
            }
        }
    }
}