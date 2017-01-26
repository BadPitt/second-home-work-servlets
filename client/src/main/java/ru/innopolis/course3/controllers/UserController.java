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
import ru.innopolis.course3.Role;
import ru.innopolis.course3.User;
import ru.innopolis.course3.UserService;
import ru.innopolis.course3.Utils;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

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
    public String showUsers(Model model, HttpSession session) {

        model.addAttribute("users",userService.getAllTransactionally());

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
                                 HttpSession session) {

        userService.addNewModelTransactionally(handleAddUser(user));

        return "redirect:/users/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(params = "delete_user",
            method = RequestMethod.POST)
    public String deleteUser(@RequestParam(name = "user_id", defaultValue = "0") long userId,
                             @RequestParam(name = "user_version", defaultValue = "0") int userVersion) {

        userService.removeModelTransactionally(userId, userVersion);

        return "redirect:/users/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(params = "edit_user",
            method = RequestMethod.POST)
    public String editUser(@RequestParam(name = "user_id", defaultValue = "0") long userId,
                           @RequestParam(name = "user_version", defaultValue = "0") int userVersion,
                           Model model) {

        model.addAttribute("user", userService.getByIdTransactionally(userId));

        return "edit_user";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(params = "confirm_edit_user",
            method = RequestMethod.POST)
    public String confirmEditUser(@RequestParam(name = "user_id", defaultValue = "0") long userId,
                             @RequestParam(name = "user_version", defaultValue = "0") int userVersion,
                             @RequestParam(name = "user_name", defaultValue = "") String name,
                             @RequestParam(name = "user_password", defaultValue = "") String password,
                             @RequestParam(name = "user_is_admin", defaultValue = "2") long roleId,
                             @RequestParam(name = "user_is_active", defaultValue = "false") boolean isActive) {
        User user = getUpdateUser(userId,
                userVersion,
                name,
                password,
                roleId,
                isActive);
        userService.updateTransactionally(user);
        if (password != null && !password.isEmpty()) {
            userService.changeUsersPassword(password, user);
        }
        return "redirect:/users/";
    }

    private User getUpdateUser(long userId,
                                  int userVersion,
                                  String name,
                                  String password,
                                  long roleId,
                                  boolean isActive) {
        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setIsActive(isActive);
        user.setVersion(userVersion);

        user.setRoles(getDefaultRoles());

        return user;
    }

    private User handleAddUser(User user) {
        String hashAndSalt = Utils.getPasswordHash(user.getPassword());
        user.setPassword(hashAndSalt);

        user.setRoles(getDefaultRoles());
        return user;
    }

    private List<Role> getDefaultRoles() {
        List<Role> auth = new ArrayList<>();
        Role role = new Role();
        role.setId(2);
        role.setName("ROLE_USER");
        auth.add(role);
        return auth;
    }
}
