package edu.sanvalero.manuel.servidor_actividad1.contexts.movies;

public class MovieApiResponseError extends RuntimeException {
    public MovieApiResponseError(String message) {
        super(message);
    }
}
