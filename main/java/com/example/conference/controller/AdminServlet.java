package com.example.conference.controller;

import com.example.conference.model.User;
import com.example.conference.service.ConferenceService;
import com.example.conference.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AdminServlet.class);
    private ConferenceService conferenceService;

    @Override
    public void init() throws ServletException {
        conferenceService = ServiceFactory.getConferenceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkAdminRole(request, response)) {
            return;
        }
        String action = request.getParameter("action");

        if ("panel".equals(action) || action == null) {
            handleAdminPanel(request, response);
        } else if ("applications".equals(action)) {

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkAdminRole(request, response)) {
            return;
        }
        String action = request.getParameter("action");

        if ("createConference".equals(action)) {

        } else if ("approve".equals(action)) {

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private boolean checkAdminRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("currentUser");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied. Admin privileges required.");
            return false;
        }
        return true;
    }

    private void handleAdminPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/admin/panel.jsp").forward(request, response);
    }
}