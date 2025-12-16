package com.example.conference.service;

import com.example.conference.dao.ApplicationDao;
import com.example.conference.dao.ConferenceDao;
import com.example.conference.dao.UserDao;

public class ServiceFactory {

    private static final UserDao USER_DAO = new UserDao();
    private static final ConferenceDao CONFERENCE_DAO = new ConferenceDao();
    private static final ApplicationDao APPLICATION_DAO = new ApplicationDao();

    private static final UserService USER_SERVICE = new UserService(USER_DAO);
    private static final ConferenceService CONFERENCE_SERVICE = new ConferenceService(CONFERENCE_DAO, APPLICATION_DAO);

    private ServiceFactory() {
    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }

    public static ConferenceService getConferenceService() {
        return CONFERENCE_SERVICE;
    }
}