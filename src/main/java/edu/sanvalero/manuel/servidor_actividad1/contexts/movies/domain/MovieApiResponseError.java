package edu.sanvalero.manuel.servidor_actividad1.contexts.movies.domain;

public class MovieApiResponseError extends RuntimeException {
    public MovieApiResponseError(String message) {
        super(message);
    }
}
