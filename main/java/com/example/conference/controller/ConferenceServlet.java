package com.example.conference.controller;

import com.example.conference.model.Conference;
import com.example.conference.service.ConferenceService;
import com.example.conference.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/conference")
public class ConferenceServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ConferenceServlet.class);
    private ConferenceService conferenceService;

    @Override
    public void init() throws ServletException {
        conferenceService = ServiceFactory.getConferenceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action) || action == null) {
            handleListConferences(request, response);
        } else if ("view".equals(action)) {

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("apply".equals(action)) {

        } else if ("withdraw".equals(action)) {

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleListConferences(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Conference> conferences = conferenceService.getAllConferences();
            request.setAttribute("conferences", conferences);
            request.getRequestDispatcher("/jsp/conference/list.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error fetching conferences list", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}