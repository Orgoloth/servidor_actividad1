package edu.sanvalero.manuel.servidor_actividad1.contexts.users.application;

import edu.sanvalero.manuel.servidor_actividad1.app.security.Constants;
import edu.sanvalero.manuel.servidor_actividad1.contexts.roles.domain.Role;
import edu.sanvalero.manuel.servidor_actividad1.contexts.roles.domain.RoleRepository;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.domain.User;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public boolean add(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreationDate(LocalDate.now());
        user.setActive(true);
        Role userRole = roleRepository.findByName(Constants.USER_ROLE);
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);

        return true;
    }

    public void remove(User user) {
        userRepository.delete(user);
    }

    public Set<User> findAll() {
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Set<User> findByCity(String city) {
        return null;
    }
}
