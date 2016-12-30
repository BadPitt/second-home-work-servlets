package ru.innopolis.course3.servlets;

import ru.innopolis.course3.servlets.handlers.ArticleServletHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Danil Popov
 */
public class ArticlesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArticleServletHandler handler = ArticleServletHandler.newHandler(req.getParameter("button"));
        getServletContext().getRequestDispatcher(handler.getPageAddress(req, resp)).forward(req, resp);
    }
}
