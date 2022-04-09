package edu.sanvalero.manuel.servidor_actividad1.contexts.movies.infrastructure;

import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.domain.MovieApiPaginatedResponse;
import edu.sanvalero.manuel.servidor_actividad1.contexts.movies.domain.MovieApiResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static edu.sanvalero.manuel.servidor_actividad1.app.TheMovieDb.API_KEY;
import static edu.sanvalero.manuel.servidor_actividad1.app.TheMovieDb.BASE_URL;

@Controller
public class MovieController {
    private static final String ERROR_PAGE = "movie/error";
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @GetMapping("/movies/popular")
    public String redirect() {
        return "redirect:/movies/popular/1";
    }

    @GetMapping(value = {"/movies/popular/{page}"})
    public String movie(Model model, RestTemplate restTemplate, @PathVariable Optional<Integer> page) throws MovieApiResponseError {
        String requestUrl = String.format("%s%s?api_key=%s&language=%s&page=%d", BASE_URL, "/movie/popular", API_KEY, LocaleContextHolder.getLocale(), page.orElse(1));
        try {
            MovieApiPaginatedResponse movieApiResponse = restTemplate.getForObject(requestUrl, MovieApiPaginatedResponse.class);
            model.addAttribute("page", movieApiResponse.getPage());
            model.addAttribute("total_pages", movieApiResponse.getTotal_pages());
            model.addAttribute("movies", movieApiResponse.getResults());
        } catch (RestClientException e) {
            logger.info(e.getLocalizedMessage());
            throw new MovieApiResponseError(e.getLocalizedMessage());
        }
        return "movies/popular";
    }

    @ExceptionHandler(MovieApiResponseError.class)
    public String handleException(HttpServletRequest request, Exception exception) {
        logger.info("Recibida excepción en movies");
        logger.info(exception.getLocalizedMessage());
        return ERROR_PAGE;
    }
}
