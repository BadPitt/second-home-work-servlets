package ru.innopolis.course3.servlets;

import ru.innopolis.course3.models.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by danil on 25/12/16.
 */
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String button = req.getParameter("button");

        if ("show_users".equals(button)) {
            req.setAttribute("users", UserService.getAllUsers());
            getServletContext().getRequestDispatcher("/users").forward(req, resp);
        } else if ("login".equals(button)) {
            getServletContext().getRequestDispatcher("/auth").forward(req, resp);
        } else if ("logout".equals(button)) {
            handleLogout(req);
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } else if ("show_articles".equals(button)) {
            getServletContext().getRequestDispatcher("/articles").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req) {
        req.getSession().removeAttribute("login_id");
        req.getSession().removeAttribute("is_admin");
        req.getSession().removeAttribute("is_active");
    }
}
