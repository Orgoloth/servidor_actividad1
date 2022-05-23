package edu.sanvalero.manuel.servidor_actividad1.contexts.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class UserController {
    public static final String EDIT_PAGE = "users/edit";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String ERROR_PAGE = "error";
    private static final String REGISTER_PAGE = "users/register";
    private static final String ME_PAGE = "users/me";
    private static final String LOGIN_PAGE = "users/login";
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return REGISTER_PAGE;
    }

    @GetMapping("/users/login")
    public String login() {
        return LOGIN_PAGE;
    }

    @GetMapping("/users/me")
    public String me(Model model, Authentication authentication, HttpServletRequest request) {
        authentication.getDetails();
        String remoteUsername = request.getRemoteUser();
        User remoteUser = userService.findByUsername(remoteUsername);
        model.addAttribute("user", remoteUser);
        return ME_PAGE;
    }

    @PostMapping("/users/register")
    public String register(@ModelAttribute User user, Model model) throws UserNotRegistered {
        boolean userAdded = userService.add(user);
        if (!userAdded) throw new UserNotRegistered("Error al registrar el usuario");

        model.addAttribute("user", user);
        return "redirect:/users/me";
    }

    @PostMapping(EDIT_PAGE)
    public String edit(@RequestParam String id, @ModelAttribute User edited) {
        User user = userService.findById(Long.parseLong(id)).orElseThrow();

        // Actualizamos los campos del formulario
        user.setName(edited.getName());
        user.setSurname(edited.getSurname());
        user.setEmail(edited.getEmail());
        user.setAddress(edited.getAddress());
        user.setPostalCode(edited.getPostalCode());
        user.setCity(edited.getCity());
        user.setProvince(edited.getProvince());
        user.setCountry(edited.getCountry());

        // Comprobamos el nuevo password, el front se ocupa de la doble comprobación en caso de rellenar el campo
        if (!Objects.equals(edited.getPassword(), "")) {
            user.setPassword(passwordEncoder.encode(edited.getPassword()));
        }

        userService.save(user); // Lanzará excepción en caso de error en el repo
        return "redirect:/";
    }
}
