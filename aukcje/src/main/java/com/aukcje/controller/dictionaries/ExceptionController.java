package com.aukcje.controller.dictionaries;

import com.aukcje.exception.CustomizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "Wystąpił nieznany błąd";

        if (ex instanceof CustomizedException)
            model.addAttribute("errorMessage", ex.getMessage());
        else
            model.addAttribute("errorMessage", errorMessage);

        return "/views/error/exception";
    }
}
