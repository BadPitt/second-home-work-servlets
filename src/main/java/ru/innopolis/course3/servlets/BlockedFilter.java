package ru.innopolis.course3.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danil Popov
 */
public class BlockedFilter implements Filter{

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
        boolean active = true;
        String url = req.getServletPath();
        if (excludeUrls.contains(url)) {
            chain.doFilter(request, response);
            return;
        }
        Object attr = req.getSession().getAttribute("is_active");
        if (attr != null) {
            active = (Boolean)attr;
        }
        if (!active) {
            req.getRequestDispatcher("/home.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
