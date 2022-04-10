package edu.sanvalero.manuel.servidor_actividad1.contexts.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    Set<User> findByCity(String city);

    Set<User> findByPostalCode(String postalCode);
}
