package com.example.conference.service;

import com.example.conference.dao.ApplicationDao;
import com.example.conference.dao.ConferenceDao;
import com.example.conference.model.Application;
import com.example.conference.model.Conference;
import com.example.conference.model.Section;
import java.sql.SQLException;
import java.util.List;

public class ConferenceService {
    private final ConferenceDao conferenceDao;
    private final ApplicationDao applicationDao;

    public ConferenceService(ConferenceDao conferenceDao, ApplicationDao applicationDao) {
        this.conferenceDao = conferenceDao;
        this.applicationDao = applicationDao;
    }

    public List<Conference> getAllConferences() {
        return conferenceDao.findAllConferences();
    }

    public List<Section> getSectionsByConferenceId(long conferenceId) {
        return conferenceDao.findSectionsByConferenceId(conferenceId);
    }

    public void submitApplication(Application application) throws SQLException {
        applicationDao.saveApplication(application);
    }

    public void withdrawApplication(long applicationId) throws SQLException {
        applicationDao.updateApplicationStatus(applicationId, Application.Status.WITHDRAWN);
    }

    public void createConference(Conference conference) throws SQLException {
        conferenceDao.saveConference(conference);
    }

    public void createSection(Section section) throws SQLException {
        conferenceDao.saveSection(section);
    }

    public List<Application> getPendingApplications() {
        return applicationDao.findAllPendingApplications();
    }

    public void approveApplication(long applicationId) throws SQLException {
        applicationDao.updateApplicationStatus(applicationId, Application.Status.APPROVED);
    }

    public void rejectApplication(long applicationId) throws SQLException {
        applicationDao.updateApplicationStatus(applicationId, Application.Status.REJECTED);
    }
}