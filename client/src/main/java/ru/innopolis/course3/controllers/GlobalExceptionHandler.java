package ru.innopolis.course3.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danil Popov
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_VIEW = "/error_page";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e)
            throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.setViewName(ERROR_VIEW);
        return mav;
    }
}
