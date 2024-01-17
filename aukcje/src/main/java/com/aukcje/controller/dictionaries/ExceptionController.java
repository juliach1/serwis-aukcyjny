package com.aukcje.controller.dictionaries;

import com.aukcje.exception.CustomizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomizedException.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "Wystąpił nieznany błąd błąd";

        if (ex instanceof CustomizedException)
            model.addAttribute("errorMessage", ex.getMessage());
        else
            model.addAttribute("errorMessage", errorMessage);

        //TODO: zrobić ładniejszą stronę obsługi wyjątków
        return "/views/error/exception";
    }
}
