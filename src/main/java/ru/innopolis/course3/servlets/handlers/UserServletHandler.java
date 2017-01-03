package ru.innopolis.course3.servlets.handlers;

import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.innopolis.course3.utils.Utils.getHashAndSaltArray;

/**
 * @author Danil Popov
 */
public abstract class UserServletHandler extends ServletHandler {

    public static UserServletHandler newHandler(String code) {
        if (code == null) {
            code = "";
        }
        switch (code) {
            case "show_users":
                return new ShowUsersHandler();
            case "add_user":
                return new AddUserHandler();
            case "confirm_add_user":
                return new ConfirmAddUserHandler();
            case "edit_user":
                return new EditUserHandler();
            case "confirm_edit_user":
                return new ConfirmEditUserHandler();
            case "delete_user":
                return new DeleteUserHandler();
            default:
                return new DefaultUserHandler();
        }
    }

    void removeUser(HttpServletRequest req) throws DBException {
        int id = Integer.parseInt(req.getParameter("user_id"));
        long version = Long.parseLong(req.getParameter("user_version"));
        UserService.removeUserById(id, version);
    }

    void updateUser(HttpServletRequest req) throws DBException {
        User user = new User();
        int id = Integer.parseInt(req.getParameter("user_id"));
        long version = Long.parseLong(req.getParameter("user_version"));

        user.setId(id);
        user.setName(req.getParameter("user_name"));
        user.setIsAdmin(Boolean.parseBoolean(req.getParameter("user_is_admin")));
        user.setIsActive(Boolean.parseBoolean(req.getParameter("user_is_active")));
        user.setVersion(version);
        UserService.updateUser(user);

        String password = req.getParameter("user_password");
        if (password != null && !password.isEmpty()) {
            UserService.changeUsersPassword(password, user);
        }
    }

    void addNewUser(HttpServletRequest req) throws DBException {
        User user = new User();
        user.setName(req.getParameter("user_name"));
        String[] hashAndSalt = getHashAndSaltArray(req.getParameter("user_password"));
        user.setPassword(hashAndSalt[0]);
        user.setSalt(hashAndSalt[1]);
        user.setIsAdmin(Boolean.parseBoolean(req.getParameter("user_is_admin")));
        user.setIsActive(Boolean.parseBoolean(req.getParameter("user_is_active")));
        UserService.addNewUser(user);
    }

    void setUsersList(HttpServletRequest req) throws DBException {
        req.setAttribute("users", UserService.getAllUsers());
    }

    protected void setUser(HttpServletRequest req) throws DBException {
        int id = Integer.parseInt(req.getParameter("user_id"));
        User user = UserService.getUserById(id);
        req.setAttribute("user", user);
    }
}

class ShowUsersHandler extends UserServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setUsersList(req);
            return "/users/users.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class DefaultUserHandler extends UserServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setUsersList(req);
            return "/users/users.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class AddUserHandler extends UserServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setUsersList(req);
            return "/users/add_user.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class ConfirmAddUserHandler extends UserServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            addNewUser(req);
            setUsersList(req);
            return "/users/users.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class EditUserHandler extends UserServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setUser(req);
            return "/users/edit_user.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class ConfirmEditUserHandler extends UserServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            updateUser(req);
            setUsersList(req);
            return "/users/users.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class DeleteUserHandler extends UserServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            removeUser(req);
            setUsersList(req);
            return "/users/users.jsp";
        } catch (DBException e) {
                return handleError(req, e.getMessage());
            }
    }
}