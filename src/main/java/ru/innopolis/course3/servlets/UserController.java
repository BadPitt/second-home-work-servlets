package ru.innopolis.course3.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.http.HttpSession;

import static ru.innopolis.course3.utils.Utils.getHashAndSaltArray;

/**
 * @author Danil Popov
 */
@Controller
@RequestMapping("/users/")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(path = "/")
    public String showUsers(Model model, HttpSession session) {
        if (session.getAttribute("login_id") == null ||
                session.getAttribute("is_admin") == null) {
            return "../home";
        }
        try {
            model.addAttribute("users",userService.getAllUsers());
        } catch (DBException e) {
            return "../error_page";
        }
        return "users";
    }

    @RequestMapping(params = "add_user",
            method = RequestMethod.POST)
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add_user";
    }

    @RequestMapping(params = "confirm_add_user",
            method = RequestMethod.POST)
    public String confirmAddUser(@Validated User user,
                                 BindingResult bindingResult,
                                 HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "../error_page";
        }
        if (session.getAttribute("login_id") == null ||
                session.getAttribute("is_admin") == null) {
            return "../home";
        }

        try {
            userService.addNewUser(handleAddUser(user));
        } catch (DBException e) {
            return "../error_page";
        }

        return "redirect:/users/";
    }

    @RequestMapping(params = "delete_user",
            method = RequestMethod.POST)
    public String deleteUser(@RequestParam(name = "user_id", defaultValue = "0") int userId,
                             @RequestParam(name = "user_version", defaultValue = "0") int userVersion) {
        try {
            userService.removeUserById(userId, userVersion);
        } catch (DBException e) {
            return "../error_page";
        }
        return "redirect:/users/";
    }

    @RequestMapping(params = "edit_user",
            method = RequestMethod.POST)
    public String editUser(@RequestParam(name = "user_id", defaultValue = "0") int userId,
                           @RequestParam(name = "user_version", defaultValue = "0") int userVersion,
                           Model model) {
        try {
            model.addAttribute("user", userService.getUserById(userId));
        } catch (DBException e) {
            return "../error_page";
        }
        return "edit_user";
    }

    @RequestMapping(params = "confirm_edit_user",
            method = RequestMethod.POST)
    public String confirmEditUser(@RequestParam(name = "user_id", defaultValue = "0") int userId,
                             @RequestParam(name = "user_version", defaultValue = "0") int userVersion,
                             @RequestParam(name = "user_name", defaultValue = "") String name,
                             @RequestParam(name = "user_password", defaultValue = "") String password,
                             @RequestParam(name = "user_is_admin", defaultValue = "false") boolean isAdmin,
                             @RequestParam(name = "user_is_active", defaultValue = "false") boolean isActive) {
        User user = getUpdateUser(userId,
                userVersion,
                name,
                password,
                isAdmin,
                isActive);
        try {
            userService.updateUser(user);
            if (password != null && !password.isEmpty()) {
                userService.changeUsersPassword(password, user);
            }
        } catch (DBException e) {
            return "../error_page";
        }
        return "redirect:/users/";
    }

    private User getUpdateUser(int userId,
                                  int userVersion,
                                  String name,
                                  String password,
                                  boolean isAdmin,
                                  boolean isActive) {
        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setIsAdmin(isAdmin);
        user.setIsActive(isActive);
        user.setVersion(userVersion);

        return user;
    }

    private User handleAddUser(User user) {
        String[] hashAndSalt = getHashAndSaltArray(user.getPassword());
        user.setPassword(hashAndSalt[0]);
        user.setSalt(hashAndSalt[1]);
        return user;
    }
}
