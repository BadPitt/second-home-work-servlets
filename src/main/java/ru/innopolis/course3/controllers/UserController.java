package ru.innopolis.course3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.services.UserService;

import javax.servlet.http.HttpSession;

import static ru.innopolis.course3.utils.Utils.getPasswordHash;

/**
 * @author Danil Popov
 */
@Controller
@RequestMapping("/users/")
public class UserController extends BaseController {

    private UserService userService;

    @Autowired
    @Qualifier("userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/")
    public String showUsers(Model model, HttpSession session) throws DBException {

        model.addAttribute("users",userService.getAllUsers());

        return "users";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(params = "add_user",
            method = RequestMethod.POST)
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add_user";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(params = "confirm_add_user",
            method = RequestMethod.POST)
    public String confirmAddUser(@ModelAttribute(name = "user") User user,
                                 HttpSession session) throws DBException {

        userService.addNewUser(handleAddUser(user));

        return "redirect:/users/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(params = "delete_user",
            method = RequestMethod.POST)
    public String deleteUser(@RequestParam(name = "user_id", defaultValue = "0") int userId,
                             @RequestParam(name = "user_version", defaultValue = "0") int userVersion) throws DBException {

        userService.removeUserById(userId, userVersion);

        return "redirect:/users/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(params = "edit_user",
            method = RequestMethod.POST)
    public String editUser(@RequestParam(name = "user_id", defaultValue = "0") int userId,
                           @RequestParam(name = "user_version", defaultValue = "0") int userVersion,
                           Model model) throws DBException {

        model.addAttribute("user", userService.getUserById(userId));

        return "edit_user";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(params = "confirm_edit_user",
            method = RequestMethod.POST)
    public String confirmEditUser(@RequestParam(name = "user_id", defaultValue = "0") int userId,
                             @RequestParam(name = "user_version", defaultValue = "0") int userVersion,
                             @RequestParam(name = "user_name", defaultValue = "") String name,
                             @RequestParam(name = "user_password", defaultValue = "") String password,
                             @RequestParam(name = "user_is_admin", defaultValue = "2") int roleId,
                             @RequestParam(name = "user_is_active", defaultValue = "false") boolean isActive) throws DBException {
        User user = getUpdateUser(userId,
                userVersion,
                name,
                password,
                roleId,
                isActive);
        userService.updateUser(user);
        if (password != null && !password.isEmpty()) {
            userService.changeUsersPassword(password, user);
        }
        return "redirect:/users/";
    }

    private User getUpdateUser(int userId,
                                  int userVersion,
                                  String name,
                                  String password,
                                  int roleId,
                                  boolean isActive) {
        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setRoleId(roleId);
        user.setIsActive(isActive);
        user.setVersion(userVersion);

        return user;
    }

    private User handleAddUser(User user) {
        String hashAndSalt = getPasswordHash(user.getPassword());
        user.setPassword(hashAndSalt);
        return user;
    }
}
