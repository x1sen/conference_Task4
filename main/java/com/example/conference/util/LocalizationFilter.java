package com.example.conference.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@WebFilter("/*")
public class LocalizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String langParam = req.getParameter("lang");
        String langSession = (String) req.getSession().getAttribute("lang");

        String finalLang = "ru";

        if (langParam != null && !langParam.trim().isEmpty()) {
            finalLang = langParam.trim();
            req.getSession().setAttribute("lang", finalLang);
        } else if (langSession != null && !langSession.trim().isEmpty()) {
            finalLang = langSession.trim();
        }

        Locale locale = new Locale(finalLang);

        request.setAttribute("lang", finalLang);
        request.setAttribute("locale", locale);

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}