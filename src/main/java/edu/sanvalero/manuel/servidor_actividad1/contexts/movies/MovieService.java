package edu.sanvalero.manuel.servidor_actividad1.contexts.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Optional<Movie> findById(long id) {
        return movieRepository.findById(id);
    }
}
