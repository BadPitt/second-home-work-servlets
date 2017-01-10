package ru.innopolis.course3.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danil Popov
 */
public class AuthFilter implements Filter {

    private List<String> excludeUrls = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("exclude_urls");
        String[] strings = urls.split(",");
        for (String s:strings) {
            excludeUrls.add(s);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String url = req.getServletPath();

        Object loginIdObject = session.getAttribute("login_id");
        Object isAdminObject = session.getAttribute("is_admin");

        String login = loginIdObject == null ? null : (String) loginIdObject;
        boolean isAdmin = isAdminObject == null ? false : (Boolean) isAdminObject;

        if (excludeUrls.contains(url) || (login != null && isAdmin)) {
            chain.doFilter(request, response);
        } else {
            req.getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
