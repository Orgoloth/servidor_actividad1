package edu.sanvalero.manuel.servidor_actividad1.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    private static Logger logger = LoggerFactory.getLogger(HomeController.class);
    private static final String ERROR_PAGE = "error";

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @ExceptionHandler()
    public String handleException(HttpServletRequest request, Exception exception) {
        logger.info("Recibida excepción en home");
        return ERROR_PAGE;
    }
}
