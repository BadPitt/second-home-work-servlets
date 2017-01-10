package ru.innopolis.course3.servlets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Danil Popov
 */
@Controller
public class HomeController {

    @RequestMapping({"/home", "/"})
    public String showHomePage() {
        return "home";
    }
}
