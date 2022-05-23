package edu.sanvalero.manuel.servidor_actividad1.app;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {

//        throw new RuntimeException("forzada");
        return "home";
    }
}
