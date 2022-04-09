package edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.domain;

import edu.sanvalero.manuel.servidor_actividad1.contexts.users.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Review findByAuthor(User author);
}
