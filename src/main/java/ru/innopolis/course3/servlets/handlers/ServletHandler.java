package ru.innopolis.course3.servlets.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Danil Popov
 */
public abstract class ServletHandler {

    public abstract String getPageAddress(HttpServletRequest req, HttpServletResponse resp);

    String handleError(HttpServletRequest req, String message) {
        req.setAttribute("error_message", message);
        return "/error_page.jsp";
    }
}
