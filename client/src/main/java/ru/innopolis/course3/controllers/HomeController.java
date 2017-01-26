package ru.innopolis.course3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Danil Popov
 */
@Controller
public class HomeController extends BaseController {

    @RequestMapping({"/home", "/"})
    public String showHomePage() {
        return "home";
    }
}
