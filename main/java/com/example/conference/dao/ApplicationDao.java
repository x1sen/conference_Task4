package com.example.conference.dao;

import com.example.conference.model.Application;
import com.example.conference.model.Application.Status;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationDao.class);

    public void saveApplication(Application application) throws SQLException {
        throw new UnsupportedOperationException("ApplicationDao.saveApplication not fully implemented.");
    }

    public void updateApplicationStatus(long applicationId, Status status) throws SQLException {
        throw new UnsupportedOperationException("ApplicationDao.updateApplicationStatus not fully implemented.");
    }

    public List<Application> findAllPendingApplications() {
        return List.of();
    }
}