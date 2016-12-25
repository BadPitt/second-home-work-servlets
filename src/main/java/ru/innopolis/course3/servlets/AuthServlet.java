package ru.innopolis.course3.servlets;

import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.innopolis.course3.utils.Utils.getPassHash;
import static ru.innopolis.course3.utils.Utils.isPassEquals;

/**
 * Created by danil on 25/12/16.
 */
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String button = req.getParameter("button");
        boolean isSuccess;

        if ("reg".equals(button)) {
            getServletContext().getRequestDispatcher("/auth/reg.jsp").forward(req, resp);
        } else if ("confirm_reg".equals(button)) {
            isSuccess = handleReg(req);
            if (isSuccess) {
                getServletContext().getRequestDispatcher("/home").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/auth/reg.jsp").forward(req, resp);
            }
        } else if ("confirm_login".equals(button)) {
            isSuccess = handleLogin(req);
            if (isSuccess) {
                getServletContext().getRequestDispatcher("/home").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/auth/login.jsp").forward(req, resp);
            }
        } else {
            getServletContext().getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }

    private boolean handleReg(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = new User();
        user.setName(req.getParameter("user_name"));
        user.setPassword(getPassHash(req.getParameter("user_password")));
        user.setIsActive(true);
        // TODO: handle errors
        UserService.addNewUser(user);
        session.setAttribute("login_id", user.getName());
        session.setAttribute("is_admin", false);
        session.setAttribute("is_active", true);

        return true;
    }

    private boolean handleLogin(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = UserService.getUserByName(req.getParameter("user_name"));
        boolean isPassEquals = isPassEquals(req.getParameter("user_password"),
                user.getPassword());
        if (isPassEquals) {
            session.setAttribute("login_id", user.getName());
            session.setAttribute("is_admin", user.isAdmin());
            session.setAttribute("is_active", user.isActive());
        }
        return isPassEquals;
    }
}
