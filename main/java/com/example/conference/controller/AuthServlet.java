package com.example.conference.controller;

import com.example.conference.model.User;
import com.example.conference.service.UserService;
import com.example.conference.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AuthServlet.class);
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = ServiceFactory.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                logger.info("User logged out.");
            }
            response.sendRedirect(request.getContextPath() + "/home");
        } else if ("register".equals(action)) {
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("register".equals(action)) {
            handleRegistration(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Email and password cannot be empty.");
            request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
            return;
        }

        Optional<User> userOpt = userService.authenticate(email, password);

        if (userOpt.isPresent()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", userOpt.get());

            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        try {
            User newUser = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .role(User.Role.PARTICIPANT)
                    .build();

            userService.registerUser(newUser, password);
            logger.info("New user registered: {}", email);

            response.sendRedirect(request.getContextPath() + "/auth?success=true");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Registration error for email: {}", email, e);
            request.setAttribute("error", "An unexpected error occurred during registration.");
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
        }
    }
}