package ru.innopolis.course3.servlets;

import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.innopolis.course3.utils.Utils.getPassHash;

/**
 * @author Danil Popov
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String button = req.getParameter("button");

        if ("show_users".equals(button)) {
            setUsersList(req);
            getServletContext().getRequestDispatcher("/users/users.jsp").forward(req, resp);
        } else if ("confirm_add_user".equals(button)) {
            addNewUser(req);
            setUsersList(req);
            getServletContext().getRequestDispatcher("/users/users.jsp").forward(req, resp);
        } else if ("add_user".equals(button)) {
            getServletContext().getRequestDispatcher("/users/add_user.jsp").forward(req, resp);
        } else if ("confirm_edit_user".equals(button)) {
            updateUser(req);
            setUsersList(req);
            getServletContext().getRequestDispatcher("/users/users.jsp").forward(req, resp);
        } else if ("edit_user".equals(button)) {
            setUser(req);
            getServletContext().getRequestDispatcher("/users/edit_user.jsp").forward(req, resp);
        } else if ("delete_user".equals(button)) {
            removeUser(req);
            setUsersList(req);
            getServletContext().getRequestDispatcher("/users/users.jsp").forward(req, resp);
        } else {
            setUsersList(req);
            getServletContext().getRequestDispatcher("/users/users.jsp").forward(req, resp);
        }
    }

    private void removeUser(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("user_id"));
        UserService.removeUserById(id);
    }

    private void updateUser(HttpServletRequest req) {
        User user = new User();
        user.setId(Integer.parseInt(req.getParameter("user_id")));
        user.setName(req.getParameter("user_name"));
        user.setIsAdmin(Boolean.parseBoolean(req.getParameter("user_is_admin")));
        user.setIsActive(Boolean.parseBoolean(req.getParameter("user_is_active")));
        UserService.updateUser(user);
    }

    private void addNewUser(HttpServletRequest req) {
        User user = new User();
        user.setName(req.getParameter("user_name"));
        user.setPassword(getPassHash(req.getParameter("user_password")));
        user.setIsAdmin(Boolean.parseBoolean(req.getParameter("user_is_admin")));
        user.setIsActive(Boolean.parseBoolean(req.getParameter("user_is_active")));
        UserService.addNewUser(user);
    }

    private void setUsersList(HttpServletRequest req) {
        req.setAttribute("users", UserService.getAllUsers());
    }

    private void setUser(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("user_id"));
        User user = UserService.getUserById(id);
        req.setAttribute("user", user);
    }
}
