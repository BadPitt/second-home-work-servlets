package ru.innopolis.course3.servlets.handlers;

import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static ru.innopolis.course3.utils.Utils.getHashAndSaltArray;
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
            case "change_password":
                return new ChangePasswordAuthHandler();
            case "confirm_change_password":
                return new ConfirmChangePasswordAuthHandler();
            default:
                return new DefaultAuthHandler();
        }
    }

    boolean handleReg(HttpServletRequest req) throws DBException {
        HttpSession session = req.getSession();
        String[] hashAndSalt = getHashAndSaltArray(req.getParameter("user_password"));
        User user = new User();
        user.setName(req.getParameter("user_name"));
        user.setPassword(hashAndSalt[0]);
        user.setSalt(hashAndSalt[1]);
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
                new String[] {user.getPassword(), user.getSalt()});
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

    void changePassword(HttpServletRequest req) throws DBException {
        int id = Integer.parseInt(req.getParameter("user_id"));
        String password = req.getParameter("user_password");
        User user = UserService.getUserById(id);
        UserService.changeUsersPassword(password, user);
    }

    void setUser(HttpServletRequest req) throws DBException {
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("login_id");
        User user = UserService.getUserByName(name);
        req.setAttribute("user", user);
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

class ChangePasswordAuthHandler extends AuthServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setUser(req);
            return "/change_password.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class ConfirmChangePasswordAuthHandler extends AuthServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            changePassword(req);
            return "/index.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}