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
import static ru.innopolis.course3.utils.Utils.isPassEquals;

/**
 * @author Danil Popov
 */
@Controller
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping("/")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(params = "reg")
    public String showRegistration(Model model) {
        model.addAttribute("user", new User());
        return "reg";
    }

    @RequestMapping(params = "confirm_login")
    public String login(@Validated User user,
                        BindingResult bindingResult,
                        HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        try {
            handleLogin(session, user);
        } catch (DBException e) {
            return "redirect:../error_page";
        }
        return "redirect:../home";
    }

    @RequestMapping(params = "confirm_reg",
            method = RequestMethod.POST)
    public String registration(@Validated User user,
                               BindingResult bindingResult,
                               HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "reg";
        }
        try {
            handleRegistration(user, session);
        } catch (DBException e) {
            return "redirect:../error_page";
        }
        return "redirect:../home";
    }

    @RequestMapping(params = "logout",
            method = RequestMethod.POST)
    public String logout(HttpSession session) {
        handleLogout(session);
        return "../home";
    }

    @RequestMapping(params = "change_password",
            method = RequestMethod.POST)
    public String changePassword(HttpSession session, Model model) {
        try {
            setUser(session, model);
        } catch (DBException e) {
            return "../error_page";
        }
        return "../change_password";
    }

    @RequestMapping(params = "confirm_change_password",
            method = RequestMethod.POST)
    public String confirmChangePassword(
            @RequestParam(name = "user_id", defaultValue = "0") int userId,
            @RequestParam(name = "user_version", defaultValue = "0") int userVersion,
            @RequestParam(name = "user_password", defaultValue = "") String password) {
        try {
            changePassword(userId, userVersion, password);
        } catch (DBException e) {
            return "../error_page";
        }
        return "../home";
    }

    private void handleRegistration(User user, HttpSession session) throws DBException {
        String[] hashAndSalt = getHashAndSaltArray(user.getPassword());
        user.setPassword(hashAndSalt[0]);
        user.setSalt(hashAndSalt[1]);
        user.setIsActive(true);

        userService.addNewUser(user);

        session.setAttribute("login_id", user.getName());
        session.setAttribute("is_admin", false);
        session.setAttribute("is_active", true);
    }

    boolean handleLogin(HttpSession session, User userFromForm) throws DBException {
        User user = userService.getUserByName(userFromForm.getName());
        boolean isPassEquals = isPassEquals(
                userFromForm.getPassword(),
                new String[] {user.getPassword(), user.getSalt()});
        if (isPassEquals) {
            session.setAttribute("login_id", user.getName());
            session.setAttribute("is_admin", user.getIsAdmin());
            session.setAttribute("is_active", user.getIsActive());
        }
        return isPassEquals;
    }

    void handleLogout(HttpSession session) {
        session.removeAttribute("login_id");
        session.removeAttribute("is_admin");
        session.removeAttribute("is_active");
    }

    void changePassword(int userId,
                        int userVersion,
                        String password) throws DBException {
        User user = userService.getUserById(userId);
        userService.changeUsersPassword(password, user);
    }

    void setUser(HttpSession session, Model model) throws DBException {
        String name = (String) session.getAttribute("login_id");
        User user = userService.getUserByName(name);
        model.addAttribute("user", user);
    }
}
