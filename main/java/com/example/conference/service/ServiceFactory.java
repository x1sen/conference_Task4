package com.example.conference.service;

import com.example.conference.dao.UserDao;

public class ServiceFactory {

    private static final UserDao USER_DAO = new UserDao();
    private static final UserService USER_SERVICE = new UserService(USER_DAO);

    private ServiceFactory() {
    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }
}