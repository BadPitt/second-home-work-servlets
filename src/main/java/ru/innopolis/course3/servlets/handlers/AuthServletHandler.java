package ru.innopolis.course3.servlets.handlers;

import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static ru.innopolis.course3.utils.Utils.getPassHash;
import static ru.innopolis.course3.utils.Utils.isPassEquals;

/**
 * @author Danil Popov
 */
public abstract class AuthServletHandler extends ServletHandler {

    public static AuthServletHandler newHandler(String code) {
        if (code == null) {
            code = "";
        }
        switch (code) {
            case "reg":
                return new RegistrationAuthHandler();
            case "confirm_reg":
                return new ConfirmRegistrationAuthHandler();
            case "confirm_login":
                return new ConfirmLoginAuthHandler();
            case "logout":
                return new LogoutAuthHandler();
            default:
                return new DefaultAuthHandler();
        }
    }

    boolean handleReg(HttpServletRequest req) throws DBException {
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

    boolean handleLogin(HttpServletRequest req) throws DBException {
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

    void handleLogout(HttpServletRequest req) {
        req.getSession().removeAttribute("login_id");
        req.getSession().removeAttribute("is_admin");
        req.getSession().removeAttribute("is_active");
    }
}

class RegistrationAuthHandler extends AuthServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        return "/auth/reg.jsp";
    }
}

class ConfirmRegistrationAuthHandler extends AuthServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            boolean isSuccess = handleReg(req);
            return isSuccess ? "/home" : "/auth/reg.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class ConfirmLoginAuthHandler extends AuthServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            boolean isSuccess = handleLogin(req);
            return isSuccess ? "/home" : "/auth/login.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class DefaultAuthHandler extends AuthServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        return "/auth/login.jsp";
    }
}

class LogoutAuthHandler extends AuthServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        handleLogout(req);
        return "/index.jsp";
    }
}