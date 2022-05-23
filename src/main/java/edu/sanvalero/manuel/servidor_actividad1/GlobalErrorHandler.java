package edu.sanvalero.manuel.servidor_actividad1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleException(NoHandlerFoundException e, Model model) {
        System.out.println("Excepción general");
        log.error(e.getMessage());
        model.addAttribute("type", "Excepción general");
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}