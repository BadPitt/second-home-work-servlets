package ru.innopolis.course3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.role.Role;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static ru.innopolis.course3.utils.Utils.getPasswordHash;

/**
 * @author Danil Popov
 */
@Controller
@RequestMapping("/auth/")
public class AuthController extends BaseController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier("authManager")
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(params = "reg")
    public String showRegistration(Model model) {
        model.addAttribute("user", new User());
        return "reg";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin() {
        return "login";
    }

    @RequestMapping(value="/login", params = "logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/home";
    }

    @RequestMapping(params = "confirm_reg",
            method = RequestMethod.POST)
    public String registration(@ModelAttribute(name = "user") User user,
                               HttpServletRequest request
                               ) throws DBException {
        String pass = user.getPassword();

        handleRegistration(user);

        autoLogin(user.getName(), pass, request);

        return "redirect:../home";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "change_password",
            method = RequestMethod.POST)
    public String changePassword(Model model) throws DBException {

        User user = userService.getUserByName(getCurrentUserName());
        model.addAttribute("user", user);

        return "../change_password";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "confirm_change_password",
            method = RequestMethod.POST)
    public String confirmChangePassword(
            @RequestParam(name = "user_id", defaultValue = "0") long userId,
            @RequestParam(name = "user_version", defaultValue = "0") int userVersion,
            @RequestParam(name = "user_password", defaultValue = "") String password
            ) throws DBException {

        changePassword(userId, userVersion, password);

        return "../home";
    }

    private void handleRegistration(User user) throws DBException {
        String hashAndSalt = getPasswordHash(user.getPassword());
        user.setPassword(hashAndSalt);
        user.setIsActive(true);

        user.setRoles(getDefaultRoles());

        userService.addNewModelTransactionally(user);
    }

    void changePassword(long userId,
                        int userVersion,
                        String password) throws DBException {
        User user = userService.getByIdTransactionally(userId);
        userService.changeUsersPassword(password, user);
    }

    private void autoLogin(String name,
                           String pass,
                           HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(name, pass);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
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
