package edu.sanvalero.manuel.servidor_actividad1.app;

import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.Movie;
import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.MovieApiResponseError;
import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class TheMovieDb {
    public static final String BASE_URL = "https://api.themoviedb.org/3";
    public static final String API_KEY = "f85cd9fd3e4ded84aabfef97d619f768";
    public static final String SEARCH_MOVIE = "/search/movie";
}
