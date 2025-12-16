package com.example.conference.util;

import com.example.conference.dao.AbstractDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class DbInitializerListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(DbInitializerListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application starting up. Initializing database...");
        try {
            ConnectionPool.getInstance();
            new AbstractDao() {}.initDatabase();
        } catch (RuntimeException e) {
            logger.error("FATAL: Database initialization failed. Application cannot start.", e);
            throw new RuntimeException("Database initialization failed.", e);
        }
        logger.info("Database initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application shutting down.");
    }
}