package edu.sanvalero.manuel.servidor_actividad1.contexts.movies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static edu.sanvalero.manuel.servidor_actividad1.app.TheMovieDb.*;

@Controller
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private static final String ERROR_PAGE = "error";
    private static final String POPULAR_PAGE = "movies/popular";
    private static final String DEFAULT_QUERY = "Batman";
    private static final int DEFAULT_PAGE = 1;

    @GetMapping(value = {"/movies/search"})
    public String movie(@RequestParam Optional<Integer> page, @RequestParam Optional<String> query, Model model, RestTemplate restTemplate) throws MovieApiResponseError {
        String effectiveQuery = query.orElse(DEFAULT_QUERY);
        String requestUrl = String.format("%s%s?api_key=%s&language=%s&page=%d&query=%s", BASE_URL, SEARCH_MOVIE, API_KEY, LocaleContextHolder.getLocale(), page.orElse(DEFAULT_PAGE), effectiveQuery);
        try {
            MovieApiPaginatedResponse movieApiResponse = restTemplate.getForObject(requestUrl, MovieApiPaginatedResponse.class);
            model.addAttribute("query", effectiveQuery);
            model.addAttribute("page", movieApiResponse.getPage());
            model.addAttribute("total_pages", movieApiResponse.getTotal_pages());
            model.addAttribute("movies", movieApiResponse.getResults());
        } catch (RestClientException e) {
            logger.info(e.getLocalizedMessage());
            throw new MovieApiResponseError(e.getLocalizedMessage());
        }
        return POPULAR_PAGE;
    }

    @ExceptionHandler(MovieApiResponseError.class)
    public String handleException(Exception exception, Model model) {
        String message = exception.getLocalizedMessage();
        logger.info("Recibida excepción en movies");
        logger.info(message);
        model.addAttribute("type", "Gestión de películas");
        model.addAttribute("message", message);
        return ERROR_PAGE;
    }
}
