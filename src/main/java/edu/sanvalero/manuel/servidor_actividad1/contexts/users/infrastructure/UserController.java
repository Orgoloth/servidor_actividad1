package edu.sanvalero.manuel.servidor_actividad1.contexts.users.infrastructure;

import edu.sanvalero.manuel.servidor_actividad1.app.HomeController;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.application.UserService;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.domain.User;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.domain.UserNotRegistered;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String ERROR_PAGE = "error";

    @Autowired
    private UserService userService;

    @GetMapping("/users/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @GetMapping("/users/login")
    public String login(Model model) {
        return "users/login";
    }

    @GetMapping("/users/me")
    public String me(Model model, HttpServletRequest request) {
        String remoteUsername = request.getRemoteUser();
        User remoteUser = userService.findByUsername(remoteUsername);
        model.addAttribute("user", remoteUser);
        return "users/me";
    }

    @PostMapping("/users/register")
    public String register(@ModelAttribute User user, Model model) throws UserNotRegistered {
        boolean userAdded = userService.add(user);
        if (!userAdded) throw new UserNotRegistered("Error al registrar el usuario");

        model.addAttribute("user", user);
        return "redirect:/users/me";
    }

    @ExceptionHandler()
    public String handleException(HttpServletRequest request, Exception exception) {
        logger.info("Recibida excepción en user");
        return ERROR_PAGE;
    }
}
