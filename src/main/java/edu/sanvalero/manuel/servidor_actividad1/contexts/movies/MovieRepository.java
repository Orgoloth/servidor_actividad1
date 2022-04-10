package edu.sanvalero.manuel.servidor_actividad1.contexts.movies;

import edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.Review;
import edu.sanvalero.manuel.servidor_actividad1.contexts.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
